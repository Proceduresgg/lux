package com.qrakn.lux.match.handler;

import lombok.Getter;
import net.jodah.expiringmap.ExpiringMap;
import org.bukkit.entity.Item;

import java.util.concurrent.TimeUnit;

@Getter
public enum DroppedItemsHandler {

    INSTANCE;

    private final ExpiringMap<Item, Long> items = ExpiringMap.builder()
            .expiration(5, TimeUnit.SECONDS)
            .asyncExpirationListener((item, l) -> ((Item) item).remove())
            .build();

    public void put(Item item) {
        items.put(item, System.currentTimeMillis());
    }
}
