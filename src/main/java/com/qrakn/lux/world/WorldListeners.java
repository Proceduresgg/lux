package com.qrakn.lux.world;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldListeners implements Listener {

    @EventHandler
    public void onWorldLoadEvent(WorldLoadEvent event) {
        event.getWorld().getEntities().forEach(Entity::remove);
    }

    @EventHandler
    public void onWeatherChangeEvent(WeatherChangeEvent event) {
        event.setCancelled(event.toWeatherState()); // prevents rain
    }

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onChunkUnloadEvent(ChunkUnloadEvent event) {
        event.setCancelled(true);
    }
}
