package com.qrakn.lux.match.queue.menu;

import com.qrakn.lux.match.ladder.MatchLadder;
import com.qrakn.phoenix.gui.menu.PlayerMenu;
import com.qrakn.phoenix.gui.menu.item.MenuItem;
import com.qrakn.phoenix.gui.menu.item.MenuItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

public class CasualMatchQueueMenu extends PlayerMenu {

    public CasualMatchQueueMenu(Player player) {
        super(player, 27, "&cCasual Queue");
    }

    @Override
    public List<MenuItem> getItems(List<MenuItem> items) {
        MatchLadder.getLadders().values()
                .forEach(ladder -> items.add(new MenuItemBuilder(ladder.getIcon().clone())
                        .name(ladder.getName())
                        .callback(ClickType.LEFT, () -> player.sendMessage(""))
                        .build()));

        return items;
    }

    @Override
    public void onClose() { }
}
