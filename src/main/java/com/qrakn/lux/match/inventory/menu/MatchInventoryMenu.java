package com.qrakn.lux.match.inventory.menu;

import com.qrakn.phoenix.gui.menu.PlayerMenu;
import com.qrakn.phoenix.gui.menu.item.MenuItem;
import com.qrakn.phoenix.gui.menu.item.MenuItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MatchInventoryMenu extends PlayerMenu {

    private final ItemStack[] contents, armor;

    public MatchInventoryMenu(Player player) {
        super(player, 54, ChatColor.YELLOW + player.getName());

        this.contents = player.getInventory().getContents().clone();
        this.armor = player.getInventory().getArmorContents().clone();
    }

    @Override
    protected void onClose() {

    }

    @Override
    public List<MenuItem> getItems(List<MenuItem> list) {
        List<MenuItem> items = new ArrayList<>();

        IntStream.rangeClosed(9, 35).forEach(i -> {
            ItemStack item = contents[i];

            items.add(i - 9, new MenuItemBuilder(item == null ? new ItemStack(Material.AIR) : item.clone()).build());
        });

        IntStream.rangeClosed(0, 8).forEach(i -> {
            ItemStack item = contents[i];

            items.add(i + 27, new MenuItemBuilder(item == null ? new ItemStack(Material.AIR) : item.clone()).build());
        });


        return items;
    }
}
