package com.qrakn.lux.match.arena.data;

import lombok.Data;

@Data
public class ArenaLocationPair {

    private final int x, z;

    @Override
    public String toString() {
        return x + ":" + z;
    }

    public static ArenaLocationPair fromString(String string) {
        String[] split = string.split(":");

        return new ArenaLocationPair(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }
}
