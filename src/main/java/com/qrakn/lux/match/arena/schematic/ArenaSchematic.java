package com.qrakn.lux.match.arena.schematic;

import com.qrakn.lux.match.arena.Arena;
import com.qrakn.lux.match.arena.data.ArenaBounds;
import com.qrakn.lux.match.arena.handler.ArenaHandler;
import com.qrakn.lux.match.arena.schematic.data.ArenaSchematicBlock;
import com.qrakn.lux.match.arena.schematic.data.ArenaSchematicLocation;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.material.MaterialData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Getter
public class ArenaSchematic {

    private final Map<ArenaSchematicLocation, ArenaSchematicBlock> blocks = new HashMap<>();

    private int width, height, length;

    private byte[] data, blocksArray;

    private NBTTagList tiles;

    private boolean loaded = false;

    public ArenaSchematic(File schematic) {
        try {
            NBTTagCompound compound = NBTCompressedStreamTools.a(new FileInputStream(schematic));

            this.width = compound.getInt("Width");
            this.height = compound.getInt("Height");
            this.length = compound.getInt("Length");
            this.data = compound.getByteArray("Data");
            this.blocksArray = compound.getByteArray("Blocks");
            this.tiles = compound.getList("TileEntities", 10);

            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < length; z++) {
                    int index = (y * length + z) * width + x;
                    int id = blocksArray[index] & 255;

                    MaterialData data = new MaterialData(id, getData()[index]);

                    if (id > 0) {
                        blocks.put(new ArenaSchematicLocation(x, y, z), new ArenaSchematicBlock(data));
                    }
                }
            }
        }

        IntStream.range(0, tiles.size()).forEach(i -> {
            NBTTagCompound tile = tiles.get(i);

            int x = tile.getInt("x");
            int y = tile.getInt("y");
            int z = tile.getInt("z");

            blocks.get(new ArenaSchematicLocation(x, y, z)).setTile(tile);
        });

        loaded = true;
    }

    public void paste(Arena arena, World world, int x, int y, int z) {
        if (!(loaded)) load();

        int maxX = x + width;
        int maxZ = z + length;
        int maxY = y + height;

        blocks.forEach((arenaLocation, arenaBlock) -> {
            Location location = new Location(world, x + arenaLocation.getX(), y + arenaLocation.getY(), z + arenaLocation.getZ());
            Block block = location.getBlock();

            block.setType(arenaBlock.getMaterial().getItemType());
            block.setData(arenaBlock.getMaterial().getData(), true);
            block.getState().update();

            NBTTagCompound tile = arenaBlock.getTile();

            if (tile != null) {
                ((CraftWorld) world).getHandle().setTileEntity(new BlockPosition(x + arenaLocation.getX(), y + arenaLocation.getY(), z + arenaLocation.getZ()), TileEntity.c(tile));
            }

            arena.setBounds(new ArenaBounds(x, maxX, z, maxZ, y, maxY));
        });
    }

    public Arena getModelArena() {
        return ArenaHandler.INSTANCE.getOrCreateModel(this);
    }
}
