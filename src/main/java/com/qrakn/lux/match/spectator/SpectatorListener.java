package com.qrakn.lux.match.spectator;

import com.qrakn.lux.match.queue.menu.CasualQueueMenu;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpectatorListener implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Profile profile = ProfileHandler.INSTANCE.getProfile(player);
        if (profile.getState() != ProfileState.SPECTATING) return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true);
        } else if (event.getItem() != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            switch (event.getItem().getType()) {
                case INK_SACK:
                    SpectatorHandler.INSTANCE.stopSpectating(player);
                    break;

                default:
                    break;
            }
        }
    }
}
