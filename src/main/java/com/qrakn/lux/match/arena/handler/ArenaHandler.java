package com.qrakn.lux.match.arena.handler;

import com.qrakn.lux.Lux;
import com.qrakn.lux.config.LuxConfig;
import com.qrakn.lux.match.arena.Arena;
import com.qrakn.lux.match.arena.data.ArenaBounds;
import com.qrakn.lux.match.arena.data.ArenaLocationPair;
import com.qrakn.lux.match.arena.schematic.ArenaSchematic;
import com.qrakn.lux.match.arena.schematic.ArenaSchematicHandler;
import com.qrakn.lux.util.FileUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.*;

@Getter
public enum ArenaHandler {

    INSTANCE;

    private final Map<ArenaLocationPair, Arena> grid = new HashMap<>();

    private World world;

    public void init() {
        world = Bukkit.getWorld("arenas");

        if (world == null) {
            world = Bukkit.createWorld(new WorldCreator("arenas")
                    .generator(Lux.getInstance().getDefaultWorldGenerator("arenas", null)));
        }

        load();
    }

    public void load() {
        File folder = new File(world.getWorldFolder().getAbsolutePath() + File.separator + "schematics");

        if (!(folder.exists())) {
            folder.mkdir();
        }

        File[] schematics = folder.listFiles();

        if (schematics != null) {
            Arrays.stream(schematics)
//                    .filter(file -> FileUtils.getFileExtension(file).equals("schematic"))
                    .forEach(schematic -> {
                        ArenaSchematicHandler.INSTANCE.handleSchematic(schematic);

                        System.out.println("LOLOL " + FileUtils.getFileName(schematic));
                    });
        }

        for (String pair : LuxConfig.ARENAS.getConfig().getStringList("ARENAS")) {
            ArenaLocationPair locationPair = ArenaLocationPair.fromString(pair);
            ArenaSchematic schematic = ArenaSchematicHandler.INSTANCE.getSchematics().get(LuxConfig.ARENAS.getString("ARENAS." + locationPair.toString() + ".SCHEMATIC"));
            List<Location> spawns = (List<Location>) LuxConfig.ARENAS.getConfig().get("ARENAS." + pair + "SPAWNS");
            ArenaBounds bounds = ArenaBounds.fromString(LuxConfig.ARENAS.getString("ARENAS." + locationPair.toString() + ".BOUNDS"));

            grid.put(locationPair, new Arena(schematic, locationPair, spawns, bounds));
        }

        ArenaSchematicHandler.INSTANCE.getSchematics().values().forEach(ArenaSchematic::getModelArena);
    }

    public void save() {
        grid.values().forEach(Arena::save);
    }

    public Arena createArena(ArenaSchematic schematic) {
        ArenaLocationPair position = getEmptyPosition(schematic);

        Arena arena = new Arena(schematic, position).pasteArena(position);

        grid.put(position, arena);

        return arena;
    }

    public ArenaLocationPair getEmptyPosition(ArenaSchematic schematic) {
        ArenaLocationPair position = new ArenaLocationPair(getRow(schematic), 0);

        while (grid.containsKey(position)) {
            position = new ArenaLocationPair(getRow(schematic), position.getZ()); // z
        }

        return position;
    }

    public int getRow(ArenaSchematic schematic) {
        for (Arena arena : grid.values()) {
            if (arena.getSchematic() == schematic) {
                ArenaBounds bounds = arena.getBounds();

                if (bounds.getMinZ() == 0) {
                    return bounds.getMinX() / 500;
                }
            }
        }

        int rows = getRows();
        return rows == 0 ? 0 : rows + 1;
    }

    public int getRows() {
        List<ArenaSchematic> counted = new ArrayList<>();

        grid.values()
                .stream()
                .filter(arena -> !(counted.contains(arena.getSchematic())))
                .forEach(arena -> counted.add(arena.getSchematic()));

        return counted.size();
    }

    public Arena getOrCreateModel(ArenaSchematic schematic) {
        ArenaLocationPair position = new ArenaLocationPair(getRow(schematic), 0);
        Arena arena = grid.get(position);

        if (arena == null) {
            arena = new Arena(schematic, position).pasteArena(position);
            grid.put(position, arena);
        }

        return arena;
    }
}
