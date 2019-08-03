package com.qrakn.lux.lobby;

import com.qrakn.lux.config.LuxConfig;
import com.qrakn.lux.match.queue.menu.CasualQueueMenu;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import com.qrakn.lux.util.MessageUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
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

        Profile profile = ProfileHandler.INSTANCE.getProfile(player);
        if (profile.getState() != ProfileState.LOBBY) return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true);
        } else if (event.getItem() != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
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
    }

    @EventHandler
    public void onWorldLoadEvent(WorldLoadEvent event) {
        event.getWorld().getEntities()
                .stream()
                .filter(it -> !(it instanceof Player))
                .forEach(Entity::remove);
    }

    @EventHandler
    public void onChunkUnloadEvent(ChunkUnloadEvent event) {
        event.setCancelled(true);
    }
}
