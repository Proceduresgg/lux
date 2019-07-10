package com.qrakn.lux.match;

import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.profile.handler.ProfileHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MatchListeners implements Listener {

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        event.getDrops().clear();

        Player player = event.getEntity();

        MatchHandler.INSTANCE.getMatch(player).handleDeath(player);
    }
}
