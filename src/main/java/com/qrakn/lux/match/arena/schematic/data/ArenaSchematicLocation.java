package com.qrakn.lux.match.arena.schematic.data;

import com.qrakn.lux.match.arena.data.ArenaBounds;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

@Data
public class ArenaSchematicLocation {

    private final int x, y, z;

    public Block getWorldBlock(ArenaBounds bounds) {
        return Bukkit.getWorld("arenas").getBlockAt(bounds.getMinX() + x, bounds.getMinY() + y, bounds.getMinZ() + z);
    }
}
