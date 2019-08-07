package com.qrakn.lux.match.spectator;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.contexts.OnlinePlayer;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SpectateCommand extends BaseCommand {

    @CommandAlias("spectate")
    public void onSpectate(Player player, OnlinePlayer target) {
        Player targetPlayer = target.player;
        Profile playerProfile = ProfileHandler.INSTANCE.getProfile(player);
        Profile targetPlayerProfile = ProfileHandler.INSTANCE.getProfile(targetPlayer);

        if (targetPlayerProfile.getState() != ProfileState.MATCH) {
            player.sendMessage(ChatColor.RED + "THEY GOTTA BE AT SPAWN !!");
        } else if (playerProfile.getState() != ProfileState.LOBBY) {
            player.sendMessage(ChatColor.RED + "YOU GOTTA BE AT SPAWN !!");
        } else {
            SpectatorHandler.INSTANCE.startSpectating(player, targetPlayer);

        }

    }
}
