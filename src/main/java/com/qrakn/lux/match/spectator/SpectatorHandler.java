package com.qrakn.lux.match.spectator;

import com.qrakn.lux.lobby.Lobby;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.match.impl.SinglesMatch;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import com.qrakn.lux.util.PlayerUtils;
import com.qrakn.phoenix.gui.menu.item.MenuItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public enum SpectatorHandler {

    INSTANCE;

    private final Map<UUID, Spectator> spectators = new HashMap<>();

    private ItemStack[] contents = {
            new MenuItemBuilder(Material.INK_SACK)
                    .durability(1)
                    .name(ChatColor.RED + "Stop Spectating")
                    .build()
                    .getItemStack()
    };

    public void startSpectating(Player spectator, Player target) {
        SinglesMatch match = MatchHandler.INSTANCE.getMatch(target);

        spectators.put(spectator.getUniqueId(), new Spectator(spectator.getUniqueId(),
                match));

        PlayerUtils.reset(spectator);

        spectator.getInventory().setContents(contents);

        match.getOpponent().hidePlayer(spectator);
        match.getPlayer().hidePlayer(spectator);

        spectator.teleport(target);
        spectator.setAllowFlight(true);
        spectator.setFlying(true);
        spectator.sendMessage(ChatColor.RED + "ha u specing now gg");

        ProfileHandler.INSTANCE.getProfile(spectator).setState(ProfileState.SPECTATING);
    }

    public void stopSpectating(Player player) {
        Lobby.spawn(player);

        SpectatorHandler.INSTANCE.getSpectators().remove(player.getUniqueId());
    }
}
