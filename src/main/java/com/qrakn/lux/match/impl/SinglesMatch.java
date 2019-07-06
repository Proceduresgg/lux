package com.qrakn.lux.match.impl;

import com.qrakn.lux.match.Match;
import com.qrakn.lux.match.arena.Arena;
import com.qrakn.lux.match.ladder.Ladder;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Getter
public class SinglesMatch extends Match {

    private final Player player, opponent;

    public SinglesMatch(Player player, Player opponent, boolean competitive, Ladder ladder, Arena arena) {
        super(competitive, ladder, arena);

        this.player = player;
        this.opponent = opponent;

        start();
    }

    private void start() {
        player.teleport(getArena().getSpawns().get(0));
        opponent.teleport(getArena().getSpawns().get(1));

        player.sendMessage(ChatColor.RED + "STARTED MATCH.");
        opponent.sendMessage(ChatColor.RED + "STARTED MATCH");
    }
}
