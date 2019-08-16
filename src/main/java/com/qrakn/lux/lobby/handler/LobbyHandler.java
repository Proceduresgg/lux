package com.qrakn.lux.lobby.handler;

import com.qrakn.lux.config.LuxConfig;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import com.qrakn.lux.util.PlayerUtils;
import com.qrakn.phoenix.gui.menu.item.MenuItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum LobbyHandler {

    INSTANCE;

    private final ItemStack[] contents = {
            new MenuItemBuilder(Material.IRON_SWORD)
                    .name(LuxConfig.ITEMS.getString("LOBBY.CASUAL_ITEM.NAME"))
                    .lore(LuxConfig.ITEMS.getString("LOBBY.CASUAL_ITEM.LORE"))
                    .build()
                    .getItemStack(),

            new MenuItemBuilder(Material.DIAMOND_SWORD)
                    .name(ChatColor.RED + "Competitive Queue")
                    .lore(ChatColor.GRAY + "Select to queue for competitive.")
                    .build()
                    .getItemStack()
    };

    public void spawn(Player player) {
        ProfileHandler.INSTANCE.get(player).setState(ProfileState.LOBBY);

        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());

        applySpawnContents(player);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            onlinePlayer.showPlayer(player);
            player.showPlayer(onlinePlayer);
        });
    }

    public void applySpawnContents(Player player) {
        PlayerUtils.reset(player);

        player.getInventory().setContents(contents);
        player.updateInventory();
    }
}
