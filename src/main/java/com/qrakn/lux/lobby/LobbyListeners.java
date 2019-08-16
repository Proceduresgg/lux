package com.qrakn.lux.lobby;

import com.qrakn.lux.lobby.handler.LobbyHandler;
import com.qrakn.lux.match.queue.menu.CasualQueueMenu;
import com.qrakn.lux.match.queue.menu.ComptetitiveQueueMenu;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;

public class LobbyListeners implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        LobbyHandler.INSTANCE.spawn(event.getPlayer());

        event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.get(player);
        ItemStack item = event.getItem();

        if (profile.getState() == ProfileState.LOBBY) {
            if (item != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                switch (item.getType()) {
                    case IRON_SWORD:
                        player.openInventory(new CasualQueueMenu(player).getInventory());
                        break;

                    case DIAMOND_SWORD:
                        player.openInventory(new ComptetitiveQueueMenu(player).getInventory());
                        break;

                    default:
                        break;
                }
            } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);
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
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Profile profile = ProfileHandler.INSTANCE.get(player);

        if (player.getGameMode() != GameMode.CREATIVE) {
            if (profile.getState() == ProfileState.LOBBY) {
                event.setCancelled(true);
            }
        }
    }
}
