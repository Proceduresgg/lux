package com.qrakn.lux.match.scenario.impl;

import com.qrakn.lux.match.Match;
import com.qrakn.lux.match.participant.MatchParticipantGroup;
import com.qrakn.lux.match.scenario.MatchScenario;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;

@RequiredArgsConstructor
public class CasualMatchScenario implements MatchScenario {

    @Override
    public void onStart(Match match) {
        StringBuilder teamNames = new StringBuilder();
        StringBuilder opponentNames = new StringBuilder();

        match.getTeam().getPlayers().forEach(player -> teamNames.append(player.getName()).append(", "));
        match.getOpponents().getPlayers().forEach(player -> opponentNames.append(player.getName()).append(", "));

        match.getPlayers().forEach(player -> player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Opponents: "
                + ChatColor.YELLOW + (match.getTeam().contains(player)
                ? opponentNames.toString().substring(0, opponentNames.length() - 2) :
                teamNames.toString().substring(0, teamNames.length() - 2))));
    }

    @Override
    public void onEnd(Match match, MatchParticipantGroup winners, MatchParticipantGroup losers) {
        Player winner = winners.getPlayers().get(0);
        Player loser = losers.getPlayers().get(0);

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