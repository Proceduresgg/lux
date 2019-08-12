package com.qrakn.lux.match.queue.handler;

import com.qrakn.lux.lobby.handler.LobbyHandler;
import com.qrakn.lux.match.ladder.Ladder;
import com.qrakn.lux.match.queue.MatchQueuePlayer;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import com.qrakn.lux.util.PlayerUtils;
import com.qrakn.phoenix.gui.menu.item.MenuItemBuilder;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public enum MatchQueueHandler {

    INSTANCE;

    private final Map<UUID, MatchQueuePlayer> players = new HashMap<>();

    private final ItemStack[] contents = {
            new MenuItemBuilder(Material.INK_SACK)
                    .durability(1)
                    .name(ChatColor.RED + "Exit Queue")
                    .build()
                    .getItemStack()
    };

    public void queue(Player player, Ladder ladder, boolean ranked) {
        ProfileHandler.INSTANCE.get(player).setState(ProfileState.QUEUE);

        PlayerUtils.reset(player);

        player.getInventory().setContents(contents);
        player.updateInventory();

        players.put(player.getUniqueId(), new MatchQueuePlayer(player, ladder.getQueue(), ranked));
    }

    public void remove(Player player) {
        ProfileHandler.INSTANCE.get(player).setState(ProfileState.LOBBY);

        LobbyHandler.INSTANCE.applySpawnContents(player); // so the player doesn't get teleported

        players.get(player.getUniqueId()).getQueue().getQueue().remove(players.remove(player.getUniqueId()));

        player.sendMessage(ChatColor.GRAY + "Exited the queue.");
    }
}
