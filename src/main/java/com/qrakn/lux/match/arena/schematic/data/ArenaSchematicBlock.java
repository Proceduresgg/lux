package com.qrakn.lux.match.arena.schematic.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.material.MaterialData;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ArenaSchematicBlock {

    private final MaterialData material;

    private NBTTagCompound tile = null;
}
