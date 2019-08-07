package com.qrakn.lux.match.queue.listener;

import com.qrakn.lux.match.queue.handler.MatchQueueHandler;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MatchQueueListeners implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (ProfileHandler.INSTANCE.getProfile(player).getState() != ProfileState.QUEUE) return;

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getItem() == null)
            return;

        switch (event.getItem().getType()) {
            case INK_SACK:
                MatchQueueHandler.INSTANCE.exit(player);
                break;

            default:
                break;
        }
    }
}
