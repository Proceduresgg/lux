package com.qrakn.lux.match.arena.schematic.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@RequiredArgsConstructor
public class LuxLocation {

    private final double x, y, z;

    private final float yaw, pitch;

    public LuxLocation(String serializedLocation) {
        String[] split = serializedLocation.split(":");

        this.x = Double.valueOf(split[0]);
        this.y = Double.valueOf(split[1]);
        this.z = Double.valueOf(split[2]);
        this.yaw = Float.valueOf(split[3]);
        this.pitch = Float.valueOf(split[4]);
    }

    public LuxLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    @Override
    public String toString() {
        return x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
    }

    public Location toBukkitLocation() {
        return new Location(Bukkit.getWorld("arenas"), x, y, z, yaw, pitch);
    }
}
