package com.qrakn.lux.match;

import com.qrakn.lux.Lux;
import com.qrakn.lux.lobby.Lobby;
import com.qrakn.lux.match.handler.EnderpearlHandler;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MatchListeners implements Listener {

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Bukkit.getScheduler().runTaskLater(Lux.getInstance(), () -> {
            player.spigot().respawn();
            Lobby.spawn(player);
        }, 6L);

        MatchHandler.INSTANCE.getMatch(player).handleDeath(player);

        event.setDeathMessage(null);
        event.getDrops().clear();
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (profile.getState() != ProfileState.MATCH || event.getItem() == null) return;

        switch (event.getItem().getType()) {
            case ENDER_PEARL:
                event.setCancelled(!EnderpearlHandler.INSTANCE.canPearl(player));
                break;

            default:
                break;
        }
    }

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (profile.getState() != ProfileState.MATCH) return;

        switch (event.getEntity().getType()) {
            case ENDER_PEARL:
                EnderpearlHandler.INSTANCE.getRecentlyPearled().put(player.getUniqueId(), System.currentTimeMillis());
                break;

            default:
                break;
        }
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);
        Item drop = event.getItemDrop();

        if (profile.getState() != ProfileState.MATCH) return;

        switch (drop.getItemStack().getType()) {
            case GLASS_BOTTLE:
                drop.remove();
                break;

            default:
                Bukkit.getScheduler().runTaskLater(Lux.getInstance(), drop::remove, 100L);
                break;
        }
    }
}
