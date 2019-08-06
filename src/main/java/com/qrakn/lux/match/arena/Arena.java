package com.qrakn.lux.match.arena;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.qrakn.lux.Lux;
import com.qrakn.lux.match.arena.command.ArenaCommand;
import com.qrakn.lux.match.arena.data.ArenaBounds;
import com.qrakn.lux.match.arena.data.ArenaLocationPair;
import com.qrakn.lux.match.arena.handler.ArenaHandler;
import com.qrakn.lux.match.arena.schematic.ArenaSchematic;
import com.qrakn.lux.match.arena.schematic.ArenaSchematicHandler;
import com.qrakn.lux.match.arena.schematic.data.ArenaSchematicBlock;
import com.qrakn.lux.match.arena.schematic.data.ArenaSchematicLocation;
import com.qrakn.lux.match.arena.schematic.data.LuxLocation;
import com.qrakn.lux.mongo.MongoHandler;
import com.qrakn.lux.util.AngleUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class Arena {

    private final ArenaSchematic schematic;

    private final ArenaLocationPair locationPair;

    private List<LuxLocation> spawns = new ArrayList<>();

    private boolean available = true;

    private ArenaBounds bounds;

    public Arena(Document document) {
        this.schematic = ArenaSchematicHandler.INSTANCE.getSchematics().get(document.getString("schematic"));
        this.locationPair = ArenaLocationPair.fromString(document.getString("locationPair"));
        this.bounds = ArenaBounds.fromString(document.getString("bounds"));

        if (!isModelArena()) {
            spawns.add(new LuxLocation(document.getString("spawn1")));
            spawns.add(new LuxLocation(document.getString("spawn2")));
        }
    }

    public Arena(ArenaSchematic schematic, ArenaLocationPair locationPair, List<LuxLocation> spawns, ArenaBounds bounds) {
        this.schematic = schematic;
        this.locationPair = locationPair;
        this.spawns = spawns;
        this.bounds = bounds;

        save();
    }

    public Document toJSON() {
        Document document = new Document("locationPair", locationPair.toString()).append("bounds", bounds.toString())
                .append("schematic", schematic.getName());

        if (!isModelArena()) {
            document.append("spawn1", spawns.get(0).toString())
                    .append("spawn2", spawns.get(1).toString());
        }
        return document;

    }

    public void save() {
        MongoHandler.INSTANCE.getDatabase().getCollection("arenas")
                .replaceOne(Filters.eq("locationPair", this.locationPair.toString()), toJSON(), new ReplaceOptions().upsert(true));
    }

    public void load(Runnable callback) {
        int minX = bounds.getMinX();
        int maxX = bounds.getMaxX();
        int minZ = bounds.getMinZ();
        int maxZ = bounds.getMaxZ();

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                ArenaHandler.INSTANCE.getWorld().getChunkAtAsync(x, z, chunk -> {
                    if (chunk.getX() == maxX && chunk.getZ() == maxZ) {
                        callback.run();
                    }
                });
            }
        }
    }

    public void copyTo(Arena arena) {
        Map<ArenaSchematicLocation, ArenaSchematicBlock> blocks = new HashMap<>();
        World world = ArenaHandler.INSTANCE.getWorld();

        for (int x = 0; x <= bounds.getWidth(); x++) {
            for (int y = 0; y <= bounds.getHeight(); y++) {
                for (int z = 0; z <= bounds.getLength(); z++) {
                    Block block = world.getBlockAt(bounds.getMinX() + x, bounds.getMinY() + y, bounds.getMinZ() + z);
                    MaterialData data = new MaterialData(block.getType(), block.getData());
                    ArenaSchematicLocation location = new ArenaSchematicLocation(x, y, z);
                    TileEntity tile = ((CraftWorld) world).getTileEntityAt(block.getX(), block.getY(), block.getZ());

                    NBTTagCompound compound = null;

                    if (tile != null) {
                        compound = new NBTTagCompound();
                        tile.b(compound);
                    }

                    blocks.put(location, new ArenaSchematicBlock(data, compound));
                }
            }
        }

        arena.copyFrom(blocks);
    }

    public void copyFrom(Map<ArenaSchematicLocation, ArenaSchematicBlock> blocks) {
        World world = ArenaHandler.INSTANCE.getWorld();

        for (int x = 0; x <= bounds.getWidth(); x++) {
            for (int y = 0; y <= bounds.getHeight(); y++) {
                for (int z = 0; z <= bounds.getLength(); z++) {
                    Block block = world.getBlockAt(bounds.getMinX() + x, bounds.getMinY() + y, bounds.getMinZ() + z);
                    ArenaSchematicLocation location = new ArenaSchematicLocation(x, y, z);
                    ArenaSchematicBlock newBlock = blocks.get(location);

                    if (newBlock != null) {
                        blocks.remove(location);

                        block.setType(newBlock.getMaterial().getItemType());
                        block.setData(newBlock.getMaterial().getData());

                        NBTTagCompound tile = newBlock.getTile();

                        if (tile != null) {
                            ((CraftWorld) world).getHandle().setTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()), TileEntity.c(tile));
                        }
                    }
                }
            }
        }

        blocks.keySet().forEach(location -> {
            ArenaSchematicBlock newBlock = blocks.get(location);
            Block block = world.getBlockAt(bounds.getMinX() + location.getX(), bounds.getMinY() + location.getY(), bounds.getMinZ() + location.getZ());

            block.setType(newBlock.getMaterial().getItemType());
            block.setData(newBlock.getMaterial().getData());

            NBTTagCompound tile = newBlock.getTile();

            if (tile != null) {
                ((CraftWorld) world).getHandle().setTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()), TileEntity.c(tile));
            }
        });

        spawns.clear();
        for (int x = 0; x <= bounds.getWidth(); x++) {
            for (int y = 0; y <= bounds.getHeight(); y++) {
                for (int z = 0; z <= bounds.getLength(); z++) {
                    Block block = world.getBlockAt(bounds.getMinX() + x, bounds.getMinY() + y, bounds.getMinZ() + z);
                    Material type = block.getType();

                    if (type == Material.SKULL) {
                        Skull skull = (Skull) block.getState();

                        if (skull.getSkullType() == SkullType.PLAYER || skull.getSkullType() == SkullType.SKELETON) {
                            Location spawnPoint = block.getLocation().clone();

                            if (block.getRelative(BlockFace.DOWN).getType() == Material.FENCE) {
                                block.getRelative(BlockFace.DOWN).setType(Material.AIR);
                            } else {
                                spawnPoint.add(0.5, 1.0, 0.5);
                            }

                            spawnPoint.setYaw(AngleUtils.faceToYaw(skull.getRotation()) + 90.0F);

                            spawns.add(new LuxLocation(spawnPoint));
                            block.setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

    public Location getLocation() {
        return new Location(ArenaHandler.INSTANCE.getWorld(), bounds.getMinX(), bounds.getMaxY(), bounds.getMinZ());
    }

    public boolean isModelArena() {
        return bounds.getMinZ() == 0;
    }

    public Arena pasteArena(ArenaLocationPair pair) {
        ArenaCommand.getExecutor().execute(() -> {
            Bukkit.getScheduler().runTask(Lux.getInstance(), () -> {
                int x = pair.getX() * 500;
                int z = pair.getZ() * 500;

                if (z == 0) {
                    schematic.paste(this, ArenaHandler.INSTANCE.getWorld(), x, 90, z);
                } else {
                    Arena model = schematic.getModelArena();
                    ArenaBounds modelBounds = model.getBounds();

                    this.bounds = new ArenaBounds(x, x + (modelBounds.getMaxX() - modelBounds.getMinX()), z,
                            z + (modelBounds.getMaxZ() - modelBounds.getMinZ()), modelBounds.getMinY(),
                            90 + (modelBounds.getMaxY() - modelBounds.getMinY())); // height = 90

                    model.copyTo(this);
                }
            });
        });

        return this;
    }
}


