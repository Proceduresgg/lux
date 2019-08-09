package com.qrakn.lux.match;

import com.qrakn.lux.Lux;
import com.qrakn.lux.lobby.handler.LobbyHandler;
import com.qrakn.lux.match.cooldown.PearlCooldown;
import com.qrakn.lux.match.handler.DroppedItemsHandler;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MatchListeners implements Listener {

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Bukkit.getScheduler().runTaskLater(Lux.getInstance(), () -> {
            player.spigot().respawn();

            LobbyHandler.INSTANCE.spawn(player);
        }, 6L);

        MatchHandler.INSTANCE.get(player).ifPresent(match -> match.handleDeath(player));

        event.setDeathMessage(null);
        event.getDrops().clear();
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.get(player);
        ItemStack item = event.getItem();

        if (profile.getState() == ProfileState.MATCH && item != null) {
            switch (item.getType()) {
                case ENDER_PEARL:
                    MatchHandler.INSTANCE.get(player).ifPresent(match -> {
                        if (match.getState() != MatchState.FIGHTING || PearlCooldown.INSTANCE.contains(player)) {
                            event.setCancelled(true);
                        }
                    });
                    break;

                default:
                    break;
            }
        }
    }

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        Profile profile = ProfileHandler.INSTANCE.get(player);

        if (profile.getState() == ProfileState.MATCH) {
            switch (event.getEntity().getType()) {
                case ENDER_PEARL:
                    PearlCooldown.INSTANCE.put(player);
                    break;

                default:
                    break;
            }
        }
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.get(player);
        Item item = event.getItemDrop();

        if (profile.getState() == ProfileState.MATCH) {
            switch (item.getItemStack().getType()) {
                case GLASS_BOTTLE:
                    item.remove();
                    break;

                default:
                    DroppedItemsHandler.INSTANCE.put(item);
                    break;
            }
        }
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        Profile profile = ProfileHandler.INSTANCE.get(player);

        if (profile.getState() == ProfileState.MATCH) {
            MatchHandler.INSTANCE.get(player).ifPresent(match -> event.setCancelled(match.getState() != MatchState.FIGHTING));
        }
    }
}
