package com.qrakn.lux.match.handler;

import com.qrakn.lux.match.inventory.menu.MatchInventoryMenu;
import lombok.Getter;
import net.jodah.expiringmap.ExpiringMap;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
public enum InventoryHandler {

    INSTANCE;

    private final ExpiringMap<UUID, MatchInventoryMenu> inventories = ExpiringMap.builder()
            .expiration(180, TimeUnit.SECONDS)
            .build();

    public void put(Player player) {
        inventories.put(player.getUniqueId(), new MatchInventoryMenu(player));
    }
}
