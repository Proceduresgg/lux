package com.qrakn.lux.match.queue.listener;

import com.qrakn.lux.match.queue.handler.MatchQueueHandler;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MatchQueueListeners implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.get(player);
        ItemStack item = event.getItem();

        if (profile.getState() == ProfileState.QUEUE && item != null) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
                switch (item.getType()) {
                    case INK_SACK:
                        MatchQueueHandler.INSTANCE.remove(player);
                        break;

                    default:
                        break;
                }
            }
        }
    }
}
