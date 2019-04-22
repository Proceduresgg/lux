package com.qrakn.lux.profile;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class ProfileListeners implements Listener {

    @EventHandler
    public void onAsyncPrePlayerLoginEvent(AsyncPlayerPreLoginEvent event) {
        Profile profile = new Profile(event.getUniqueId(), event.getName()).load();
    }
}
