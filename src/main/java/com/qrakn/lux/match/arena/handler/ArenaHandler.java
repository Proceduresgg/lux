package com.qrakn.lux.match.arena.handler;

import com.qrakn.lux.Lux;
import com.qrakn.lux.match.arena.Arena;
import com.qrakn.lux.match.arena.schematic.ArenaSchematicHandler;
import com.qrakn.lux.util.FileUtils;
import javafx.util.Pair;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
}
