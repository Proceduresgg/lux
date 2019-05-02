package com.qrakn.lux.match.ladder;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("ladders")
@CommandPermission("com.qrakn.lux.developer")
public class MatchLadderCommand extends BaseCommand {

    @Subcommand("create")
    public void onCreate(Player player, String name) {
        MatchLadder.createLadder(name);
    }
}
