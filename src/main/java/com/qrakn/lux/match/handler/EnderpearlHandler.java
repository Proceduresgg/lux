package com.qrakn.lux.match.handler;

import com.qrakn.lux.Lux;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public enum EnderpearlHandler {

    INSTANCE;

    private final Map<UUID, Long> recentlyPearled = new HashMap<>();

    private final int cooldown = 16;

    public void init() {
        Bukkit.getScheduler().runTaskTimer(Lux.getInstance(), () -> recentlyPearled.keySet().forEach(uuid -> {
            if (getEndTime(uuid) < System.currentTimeMillis()) {
                recentlyPearled.remove(uuid);
            }
        }), 0, 5);
    }

    public boolean canPearl(Player player) {
        return !(recentlyPearled.containsKey(player.getUniqueId()));
    }

    public long getEndTime(UUID uuid) {
        return recentlyPearled.get(uuid) + cooldown * 1000;
    }
}
