package com.qrakn.lux.match.queue.handler;

import com.qrakn.lux.Lux;
import com.qrakn.lux.match.ladder.Ladder;
import com.qrakn.lux.match.ladder.handler.LadderHandler;
import com.qrakn.lux.match.queue.MatchQueue;
import com.qrakn.lux.match.queue.MatchQueuePlayer;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import com.qrakn.phoenix.gui.menu.item.MenuItemBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum MatchQueueHandler {

    INSTANCE;

    private ItemStack[] contents = {
            new MenuItemBuilder(Material.INK_SACK)
                    .durability(1)
                    .name(ChatColor.RED + "Exit Queue")
                    .build()
                    .getItemStack()
    };

    public void queue(Player player, Ladder ladder, boolean ranked) {
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        profile.setState(ProfileState.QUEUE);

        player.getInventory().clear();
        player.getInventory().setContents(contents);
        player.updateInventory();

        ladder.getQueue().getQueue().add(new MatchQueuePlayer(player, ranked));
    }
}
