package com.qrakn.lux.match.spectator.handler;

import com.qrakn.lux.lobby.handler.LobbyHandler;
import com.qrakn.lux.match.Match;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.match.spectator.Spectator;
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
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum SpectatorHandler {

    INSTANCE;

    private final Map<UUID, Spectator> spectators = new ConcurrentHashMap<>();

    private final ItemStack[] contents = {
            new MenuItemBuilder(Material.INK_SACK)
                    .durability(1)
                    .name(ChatColor.RED + "Stop Spectating")
                    .build()
                    .getItemStack()
    };

    public void spectate(Player spectator, Player target) {
        MatchHandler.INSTANCE.get(target).ifPresent(match -> {
            ProfileHandler.INSTANCE.get(spectator).setState(ProfileState.SPECTATING);

            spectators.put(spectator.getUniqueId(), new Spectator(spectator.getUniqueId(), match));

            PlayerUtils.reset(spectator);

            spectator.getInventory().setContents(contents);

            match.getPlayers().forEach(player -> player.hidePlayer(spectator));

            spectator.teleport(target);
            spectator.setAllowFlight(true);
            spectator.setFlying(true);
            spectator.sendMessage(ChatColor.GREEN + "You're now spectating " + target.getName());
        });

    }

    public void remove(Player player) {
        spectators.remove(player.getUniqueId());

        LobbyHandler.INSTANCE.spawn(player);
    }

    public void handleMatch(Match match) {
        spectators.values()
                .stream()
                .filter(spectator -> spectator.getMatch() == match)
                .forEach(spectator -> remove(Bukkit.getPlayer(spectator.getUuid())));
    }
}
