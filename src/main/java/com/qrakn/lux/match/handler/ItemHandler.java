package com.qrakn.lux.match.handler;

import com.qrakn.lux.Lux;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public enum ItemHandler {

    INSTANCE;

    private final Map<Item, Long> drops = new HashMap<>();

    public void init() {
        Bukkit.getScheduler().runTaskTimer(Lux.getInstance(), () -> drops.keySet().forEach(drop -> {
            if (drops.get(drop) + 5000 < System.currentTimeMillis()) {
                drop.remove();
                drops.remove(drop);
            }
        }), 0, 5);
    }
}
