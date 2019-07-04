package com.qrakn.lux.match.arena.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArenaBounds {

    private final int minX, maxX, minZ, maxZ, minY, maxY;

    public int getWidth() { return maxX - minX; }

    public int getHeight() { return maxY - minY; }

    public int getLength() { return maxZ - minZ; }
}
