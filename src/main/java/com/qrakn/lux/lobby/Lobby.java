package com.qrakn.lux.lobby;

import com.qrakn.lux.util.PlayerUtils;
import com.qrakn.phoenix.gui.menu.item.MenuItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Lobby {

    private static ItemStack[] contents = {
            new MenuItemBuilder(Material.IRON_AXE)
                    .name("")
                    .lore()
                    .build()
                    .getItemStack()
    };

    public static void spawn(Player player) {
        PlayerUtils.reset(player);

        player.getInventory().setContents(contents);
    }
}
