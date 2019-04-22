package com.qrakn.lux.lobby;

import com.qrakn.lux.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Lobby {

    private static ItemStack[] contents = {};

    public static void spawn(Player player) {
        PlayerUtils.reset(player);

        player.getInventory().setContents(contents);
    }
}
