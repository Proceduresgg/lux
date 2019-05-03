package com.qrakn.lux.profile;

import com.qrakn.lux.profile.handler.ProfileHandler;
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

public class ProfileListeners implements Listener {

    @EventHandler
    public void onAsyncPrePlayerLoginEvent(AsyncPlayerPreLoginEvent event) {
        Profile profile = new Profile(event.getUniqueId(), event.getName()).load();

        ProfileHandler.INSTANCE.getProfiles().put(event.getUniqueId(), profile);
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (profile.getState().isLoseHunger()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (profile.getState().isTakeDamage()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (profile.getState().isDealDamage()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (profile.getState().isDropItem()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (profile.getState().isPickupItem()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (profile.getState().isPlaceBlock()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (profile.getState().isBreakBlock()) return;

        event.setCancelled(true);
    }
}
