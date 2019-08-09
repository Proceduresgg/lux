package com.qrakn.lux.match.spectator;

import com.qrakn.lux.match.spectator.handler.SpectatorHandler;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpectatorListeners implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.get(player);
        Item item = (Item) event.getItem();

        if (profile.getState() == ProfileState.SPECTATING) {
            if (item != null  && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                switch (event.getItem().getType()) {
                    case INK_SACK:
                        SpectatorHandler.INSTANCE.remove(player);
                        break;

                    default:
                        break;
                }
            }
        }
    }
}
