package com.qrakn.lux.lobby;

import com.qrakn.lux.config.LuxConfig;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import com.qrakn.lux.util.PlayerUtils;
import com.qrakn.phoenix.gui.menu.item.MenuItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Lobby {

    private static ItemStack[] contents = {
            new MenuItemBuilder(Material.IRON_SWORD)
                    .name(LuxConfig.ITEMS.getString("LOBBY.CASUAL_ITEM.NAME"))
                    .lore(LuxConfig.ITEMS.getString("LOBBY.CASUAL_ITEM.LORE"))
                    .build()
                    .getItemStack()
    };

    public static void spawn(Player player) {
        ProfileHandler.INSTANCE.getProfile(player).setState(ProfileState.LOBBY);

        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());

        applySpawnContents(player);
    }

    public static void applySpawnContents(Player player) {
        PlayerUtils.reset(player);

        player.getInventory().setContents(contents);

        player.updateInventory();
    }
}
