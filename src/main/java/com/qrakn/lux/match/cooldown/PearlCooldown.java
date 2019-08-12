package com.qrakn.lux.match.cooldown;

import lombok.Getter;
import net.jodah.expiringmap.ExpiringMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
public enum PearlCooldown {

    INSTANCE;

    private final ExpiringMap<UUID, Long> recentlyPearled = ExpiringMap.builder()
            .expiration(16, TimeUnit.SECONDS)
            .asyncExpirationListener((uuid, l) -> Bukkit.getPlayer((UUID) uuid).sendMessage(ChatColor.YELLOW + "You may now pearl!"))
            .build();

    public boolean contains(Player player) {
        return recentlyPearled.containsKey(player.getUniqueId());
    }

    public void put(Player player) {
        recentlyPearled.put(player.getUniqueId(), System.currentTimeMillis());
    }
}
