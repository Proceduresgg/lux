package com.qrakn.lux.match.queue.menu;

import com.qrakn.lux.config.LuxConfig;
import com.qrakn.lux.match.ladder.handler.LadderHandler;
import com.qrakn.lux.match.queue.handler.MatchQueueHandler;
import com.qrakn.phoenix.gui.menu.PlayerMenu;
import com.qrakn.phoenix.gui.menu.item.MenuItem;
import com.qrakn.phoenix.gui.menu.item.MenuItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

public class ComptetitiveQueueMenu extends PlayerMenu {

    public ComptetitiveQueueMenu(Player player) {
        super(player, 9, ChatColor.RED + "Competitive Queue");
    }

    @Override
    public List<MenuItem> getItems(List<MenuItem> items) {
        LadderHandler.INSTANCE.getLadders().values()
                .forEach(ladder -> items.add(new MenuItemBuilder(ladder.getIcon().clone())
                        .name(LuxConfig.ITEMS.getString("QUEUE.CASUAL.COLOR.NAME") + ladder.getName())
                        .lore(LuxConfig.ITEMS.getString("QUEUE.CASUAL.COLOR.LORE_1") + "Playing: " + LuxConfig.ITEMS.getString("QUEUE.CASUAL.COLOR.LORE_2") + ladder.getPlaying())
                        .lore(LuxConfig.ITEMS.getString("QUEUE.CASUAL.COLOR.LORE_1") + "Queuing: " + LuxConfig.ITEMS.getString("QUEUE.CASUAL.COLOR.LORE_2") + ladder.getQueuing())
                        .callback(ClickType.LEFT, () -> {
                            MatchQueueHandler.INSTANCE.queue(player, ladder, true);

                            player.closeInventory();

                            player.sendMessage(ChatColor.GRAY + "Added you to the " + ChatColor.GOLD + ladder.getName() + ChatColor.GRAY + " queue!");
                        })
                        .build()));

        return items;
    }

    @Override
    public void onClose() {
    }
}
