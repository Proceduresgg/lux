package com.qrakn.lux.profile;

import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.match.queue.handler.MatchQueueHandler;
import com.qrakn.lux.profile.handler.ProfileHandler;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListeners implements Listener {

    @EventHandler
    public void onAsyncPrePlayerLoginEvent(AsyncPlayerPreLoginEvent event) {
        ProfileHandler.INSTANCE.create(event.getUniqueId(), event.getName());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfiles().remove(player.getUniqueId());

        switch (profile.getState()) {
            case MATCH:
                MatchHandler.INSTANCE.getMatch(player).handleDeath(player);
                return;

            case QUEUE:
                MatchQueueHandler.INSTANCE.exit(player);
        }
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (!profile.getState().isLoseHunger()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (!profile.getState().isTakeDamage()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (!profile.getState().isDealDamage()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (!profile.getState().isDropItem()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (!profile.getState().isPickupItem()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (player.getGameMode() == GameMode.CREATIVE) return;

        if (profile.getState().isPlaceBlock()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (player.getGameMode() == GameMode.CREATIVE) return;

        if (!profile.getState().isBreakBlock()) {
            event.setCancelled(true);
        }
    }
}
