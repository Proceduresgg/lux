package com.qrakn.lux.match.inventory.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.qrakn.lux.match.handler.InventoryHandler;
import com.qrakn.lux.match.inventory.menu.MatchInventoryMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("")
public class InventoryCommand extends BaseCommand {

    @CommandAlias("inventory")
    public void onInventory(Player player, OnlinePlayer onlinePlayer) {
        MatchInventoryMenu inventory = InventoryHandler.INSTANCE.getInventories().get(onlinePlayer.getPlayer().getUniqueId());

        if (inventory == null) {
            player.sendMessage(ChatColor.RED + "not being stored anymore fuck off");
        } else {
            player.openInventory(inventory.getInventory());
        }
    }
}
