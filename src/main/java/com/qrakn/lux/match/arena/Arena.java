package com.qrakn.lux.match.arena;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private final List<Location> spawns = new ArrayList<>();

    private boolean avaiable = true;

    private MatchArenaBounds bounds;

    @Getter
    @RequiredArgsConstructor
    class MatchArenaBounds {

        private final int minX, maxX, minZ, maxZ, minY, maxY;
    }
}


