package com.qrakn.lux.lobby;

import com.qrakn.lux.config.LuxConfig;
import com.qrakn.lux.match.queue.menu.CasualQueueMenu;
import com.qrakn.lux.util.MessageUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class LobbyListeners implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Lobby.spawn(player);

        LuxConfig.MESSAGES.getFileConfiguration().getStringList("JOIN-MESSAGES")
                .stream()
                .map(MessageUtils::color)
                .forEach(player::sendMessage);

        event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        switch (event.getItem().getType()) {
            case IRON_SWORD:
                player.openInventory(new CasualQueueMenu(player).getInventory());
                break;

            case DIAMOND_AXE:
                break;

            default:
                break;
        }
    }

    @EventHandler
    public void onWorldLoadEvent(WorldLoadEvent event) {
        event.getWorld().getEntities()
                .stream()
                .filter(it -> !(it instanceof Player))
                .forEach(Entity::remove);
    }
}
