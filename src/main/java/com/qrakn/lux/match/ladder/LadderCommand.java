package com.qrakn.lux.match.ladder;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.qrakn.lux.match.kit.MatchKit;
import com.qrakn.lux.match.ladder.handler.LadderHandler;
import org.bukkit.entity.Player;

@CommandAlias("ladders")
@CommandPermission("com.qrakn.lux.developer")
public class LadderCommand extends BaseCommand {

    @Subcommand("create")
    public void onCreate(Player player, String name) {
        LadderHandler.INSTANCE.createLadder(name);
    }

    @Subcommand("setinventory")
    public void onSetInventory(Player player, String name) {
        Ladder ladder = LadderHandler.INSTANCE.getLadders().get(name);

        ladder.setDefaultKit(new MatchKit(player.getInventory().getContents(), player.getInventory().getArmorContents()));
    }

    @Subcommand("getinventory")
    public void onGetInventory(Player player, String name) {
        Ladder ladder = LadderHandler.INSTANCE.getLadders().get(name);

        ladder.getDefaultKit().apply(player);
    }

    @Subcommand("delete")
    public void onDelete(Player player, String name) {
        LadderHandler.INSTANCE.getLadders().get(name).delete();
    }

    @Subcommand("seticon")
    public void onSetIcon(Player player, String name) {
        LadderHandler.INSTANCE.getLadders().get(name).setIcon(player.getItemInHand());
    }
}
