package com.qrakn.lux.match.arena.handler;

import com.qrakn.lux.Lux;
import com.qrakn.lux.match.arena.Arena;
import com.qrakn.lux.match.arena.data.ArenaBounds;
import com.qrakn.lux.match.arena.schematic.ArenaSchematic;
import com.qrakn.lux.match.arena.schematic.ArenaSchematicHandler;
import com.qrakn.lux.util.FileUtils;
import javafx.util.Pair;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.*;

@Getter
public enum ArenaHandler {

    INSTANCE;

    private final Map<Pair<Integer, Integer>, Arena> grid = new HashMap<>();

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
                    .filter(file -> FileUtils.getFileExtension(file).equals("schematic"))
                    .forEach(ArenaSchematicHandler.INSTANCE::handleSchematic);
        }
    }

    public Arena createArena(ArenaSchematic schematic) {
        Pair<Integer, Integer> position = getEmptyPosition(schematic);

        Arena arena = new Arena(schematic).pasteArena(position);

        grid.put(position, arena);

        return arena;
    }

    public Pair<Integer, Integer> getEmptyPosition(ArenaSchematic schematic) {
        Pair<Integer, Integer> position = new Pair<>(getRow(schematic), 0);

        while (grid.containsKey(position)) {
            position = new Pair<>(getRow(schematic), position.getValue()); // z
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

        switch (getRows()) {
            case 0:
                return 0;

            default:
                return getRows() + 1;
        }
    }

    public int getRows() {
        List<ArenaSchematic> counted = new ArrayList<>();

        grid.values()
                .stream()
                .filter(arena -> !(counted.contains(arena.getSchematic())))
                .forEach(arena -> counted.add(arena.getSchematic()));

        return counted.size();
    }
}
