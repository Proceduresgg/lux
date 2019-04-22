package com.qrakn.lux.lobby;

import com.qrakn.lux.config.LuxConfiguration;
import com.qrakn.lux.match.queue.menu.CasualMatchQueueMenu;
import com.qrakn.lux.util.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class LobbyListeners implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Lobby.spawn(player);

        LuxConfiguration.getMessages().getConfig().getStringList("join-messages")
                .forEach(message -> player.sendMessage(MessageUtils.color(message)));
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            switch (event.getItem().getType()) {
                case IRON_AXE:
                    player.openInventory(new CasualMatchQueueMenu(player).getInventory());
                    break;

                case DIAMOND_AXE: break;

                default: break;
            }
        }
    }
}
