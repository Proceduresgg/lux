package com.qrakn.lux.lobby;

import com.qrakn.lux.config.LuxConfig;
import com.qrakn.lux.util.PlayerUtils;
import com.qrakn.phoenix.gui.menu.item.MenuItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Lobby {

    private static ItemStack[] contents = {
            new MenuItemBuilder(Material.IRON_SWORD)
                    .name(LuxConfig.getColor("LOBBY.CASUAL_ITEM.NAME"))
                    .lore(LuxConfig.getColor("LOBBY.CASUAL_ITEM.LORE"))
                    .build()
                    .getItemStack()
    };

    public static void spawn(Player player) {
        PlayerUtils.reset(player);

        player.getInventory().setContents(contents);

        player.updateInventory();
    }
}
