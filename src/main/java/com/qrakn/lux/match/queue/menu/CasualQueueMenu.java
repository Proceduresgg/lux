package com.qrakn.lux.match.queue.menu;

import com.qrakn.lux.config.LuxConfig;
import com.qrakn.lux.match.ladder.handler.LadderHandler;
import com.qrakn.lux.match.queue.handler.MatchQueueHandler;
import com.qrakn.phoenix.gui.menu.PlayerMenu;
import com.qrakn.phoenix.gui.menu.item.MenuItem;
import com.qrakn.phoenix.gui.menu.item.MenuItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

public class CasualQueueMenu extends PlayerMenu {

    public CasualQueueMenu(Player player) {
        super(player, 27, LuxConfig.ITEMS.getString("QUEUE.CASUAL.TITLE"));
    }

    @Override
    public List<MenuItem> getItems(List<MenuItem> items) {
        LadderHandler.INSTANCE.getLadders().values()
                .forEach(ladder -> items.add(new MenuItemBuilder(ladder.getIcon().clone())
                        .name(LuxConfig.ITEMS.getString("QUEUE.CASUAL.COLOR.NAME") + ladder.getName())
                        .callback(ClickType.LEFT, () -> {
                            MatchQueueHandler.INSTANCE.queue(player, ladder, false);

                            player.closeInventory();

                            player.sendMessage(LuxConfig.ITEMS.getColoredString("QUEUE.CASUAL.JOIN-QUEUE"));
                        })
                        .build()));

        return items;
    }

    @Override
    public void onClose() {
    }
}
