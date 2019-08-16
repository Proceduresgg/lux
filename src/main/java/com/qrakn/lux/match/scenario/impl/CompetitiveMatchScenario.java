package com.qrakn.lux.match.scenario.impl;

import com.qrakn.lux.match.Match;
import com.qrakn.lux.match.participant.MatchParticipantGroup;
import com.qrakn.lux.match.scenario.MatchScenario;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.handler.ProfileHandler;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.StringJoiner;

@RequiredArgsConstructor
public class CompetitiveMatchScenario implements MatchScenario {

    @Override
    public void onStart(Match match) {
        StringJoiner team = new StringJoiner(",");
        StringJoiner opponents = new StringJoiner(",");

        match.getTeam().getPlayers().forEach(player -> team.add(player.getName() + ProfileHandler.INSTANCE.get(player).getRating(match.getLadder())));
        match.getOpponents().getPlayers().forEach(player -> opponents.add(player.getName() + ProfileHandler.INSTANCE.get(player).getRating(match.getLadder())));

        match.getPlayers().forEach(player -> player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Opponents: "
                + ChatColor.YELLOW + (match.getTeam().contains(player)
                ? opponents.toString() : team.toString())));
    }

    @Override
    public void onEnd(Match match, MatchParticipantGroup winners, MatchParticipantGroup losers) {
        Player winner = winners.getPlayers().get(0);
        Player loser = losers.getPlayers().get(0);

        Profile winnerProfile = ProfileHandler.INSTANCE.get(winner);
        Profile loserProfile = ProfileHandler.INSTANCE.get(loser);

        int oldWinnerElo = winnerProfile.getRating(match.getLadder());

        winnerProfile.getLadderData().get(match.getLadder()).updateRating(loserProfile.getRating(match.getLadder()),
                true);
        loserProfile.getLadderData().get(match.getLadder()).updateRating(oldWinnerElo, false);

        Arrays.asList(winner, loser)
                .forEach(it -> {
                    it.sendMessage(ChatColor.GOLD + "Winner: " + ChatColor.YELLOW + winner.getName());
                    it.spigot().sendMessage(new ComponentBuilder("Inventories: ").color(net.md_5.bungee.api.ChatColor.AQUA)
                            .append(winner.getName()).color(net.md_5.bungee.api.ChatColor.GREEN)
                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to view.").color(net.md_5.bungee.api.ChatColor.GRAY).create()))
                            .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inventory " + winner.getName()))
                            .append(", ").color(net.md_5.bungee.api.ChatColor.AQUA)
                            .append(loser.getName()).color(net.md_5.bungee.api.ChatColor.RED)
                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to view.").color(net.md_5.bungee.api.ChatColor.GRAY).create()))
                            .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inventory " + loser.getName()))
                            .create());
                });
    }
}
