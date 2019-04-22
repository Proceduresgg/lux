package com.qrakn.lux.match.queue.menu;

import com.qrakn.phoenix.gui.menu.PlayerMenu;
import com.qrakn.phoenix.gui.menu.item.MenuItem;
import org.bukkit.entity.Player;

import java.util.List;

public class CasualMatchQueueMenu extends PlayerMenu {

    public CasualMatchQueueMenu(Player player) {
        super(player, 27, "&cCasual Queue");
    }

    @Override
    public List<MenuItem> getItems(List<MenuItem> list) {
        return null;
    }

    @Override
    protected void onClose() {

    }
}
