package com.qrakn.lux.match.arena.schematic;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Getter
public class ArenaSchematic {

    private int width, height, length;

    public ArenaSchematic(File schematic) {
        try {
            NBTTagCompound compound = NBTCompressedStreamTools.a(new FileInputStream(schematic));

            this.width = compound.getInt("Width");
            this.height = compound.getInt("Height");
            this.length = compound.getInt("Length");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
