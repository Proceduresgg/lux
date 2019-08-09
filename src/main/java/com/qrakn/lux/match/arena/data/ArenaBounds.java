package com.qrakn.lux.match.arena.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArenaBounds {

    private final int minX, maxX, minZ, maxZ, minY, maxY;

    public int getWidth() {
        return maxX - minX;
    }

    public int getHeight() {
        return maxY - minY;
    }

    public int getLength() {
        return maxZ - minZ;
    }

    @Override
    public String toString() {
        return minX + ":" + maxX + ":" + minZ + ":" + maxZ + ":" + minY + ":" + maxY;
    }

    public static ArenaBounds fromString(String string) {
        String[] split = string.split(":");

        return new ArenaBounds(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]),
                Integer.valueOf(split[3]), Integer.valueOf(split[4]), Integer.valueOf(split[5]));
    }
}
