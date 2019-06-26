package com.qrakn.lux.match.arena.schematic;

import com.qrakn.lux.util.FileUtils;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum ArenaSchematicHandler {

    INSTANCE;

    private final Map<String, ArenaSchematic> schematics = new HashMap<>();

    public void handleSchematic(File schematic) {
        schematics.put(FileUtils.getFileName(schematic), new ArenaSchematic(schematic));
    }
}
