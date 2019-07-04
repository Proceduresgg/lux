package com.qrakn.lux.util;

import org.bukkit.block.BlockFace;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class AngleUtils {

    private static Map<BlockFace, Integer> notches = new EnumMap<>(BlockFace.class);

    static {
        BlockFace[] radials = {
                BlockFace.WEST, BlockFace.NORTH_WEST, BlockFace.NORTH,
                BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST,
                BlockFace.SOUTH, BlockFace.SOUTH_WEST
        };

        for (int x = 0; x < radials.length; x++) {
            notches.put(radials[x], x);
        }
    }

    public static int faceToYaw(BlockFace face) {
        return angle(45 * (notches.get(face)));
    }

    private static int angle(int angle) {
        int wrappedAngle = angle;

        while (wrappedAngle <= -180) {
            wrappedAngle += 360;
        }

        while (wrappedAngle > 180) {
            wrappedAngle -= 360;
        }

        return wrappedAngle;
    }
}
