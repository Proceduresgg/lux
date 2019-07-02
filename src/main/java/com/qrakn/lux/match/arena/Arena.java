package com.qrakn.lux.match.arena;

import com.qrakn.lux.match.arena.data.ArenaBounds;
import com.qrakn.lux.match.arena.handler.ArenaHandler;
import com.qrakn.lux.match.arena.schematic.ArenaSchematic;
import javafx.util.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Arena {

    private final List<Location> spawns = new ArrayList<>();

    private final ArenaSchematic schematic;

    private boolean avaiable = true;

    private ArenaBounds bounds;

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

    }

    public Location getLocation() {
        return new Location(ArenaHandler.INSTANCE.getWorld(), bounds.getMinX(), bounds.getMaxY(), bounds.getMinZ());
    }

    public boolean isModelArena() {
        return bounds.getMinZ() == 0;
    }

    public Arena pasteArena(Pair<Integer, Integer> pair) {
        int x = pair.getKey() * 500;
        int z = pair.getValue() * 500;

        schematic.paste(this, ArenaHandler.INSTANCE.getWorld(), x, 90, z);

        return this;
    }
}


