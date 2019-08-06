package com.qrakn.lux.match.arena.schematic.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@RequiredArgsConstructor
public class LuxLocation {

    private final double x, y, z;

    public LuxLocation(String serializedLocation) {
        String[] split = serializedLocation.split(":");

        this.x = Double.valueOf(split[0]);
        this.y = Double.valueOf(split[1]);
        this.z = Double.valueOf(split[2]);
    }

    public LuxLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    @Override
    public String toString() {
        return x + ":" + y + ":" + z;
    }

    public Location toBukkitLocation() {
        return new Location(Bukkit.getWorld("arenas"), x, y, z);
    }
}
