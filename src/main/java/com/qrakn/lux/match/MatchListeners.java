package com.qrakn.lux.match;

import com.qrakn.lux.match.handler.EnderpearlHandler;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import com.qrakn.lux.util.PlayerUtils;
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
        event.setDeathMessage(null);
        event.getDrops().clear();

        Player player = event.getEntity();

        MatchHandler.INSTANCE.getMatch(player).handleDeath(player);

        player.setHealth(20);

        PlayerUtils.reset(player);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        if (profile.getState() != ProfileState.MATCH) return;

        switch (event.getItem().getType()) {
            case ENDER_PEARL:
                event.setCancelled(EnderpearlHandler.INSTANCE.canPearl(player));
                break;

            default: break;
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

            default: break;
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

        }
    }
}
