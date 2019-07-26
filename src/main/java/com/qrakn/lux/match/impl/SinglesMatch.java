package com.qrakn.lux.match.impl;

import com.qrakn.lux.Lux;
import com.qrakn.lux.lobby.Lobby;
import com.qrakn.lux.match.Match;
import com.qrakn.lux.match.arena.Arena;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.match.ladder.Ladder;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;

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

        player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Opponent: " + ChatColor.YELLOW + opponent.getName());
        opponent.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Opponent: " + ChatColor.YELLOW + player.getName());

        Profile playerProfile = ProfileHandler.INSTANCE.getProfile(player);
        Profile opponentProfile = ProfileHandler.INSTANCE.getProfile(opponent);

        playerProfile.setState(ProfileState.MATCH);
        opponentProfile.setState(ProfileState.MATCH);

        getLadder().getDefaultKit().apply(player);
        getLadder().getDefaultKit().apply(opponent);

        player.showPlayer(opponent);
        opponent.showPlayer(player);
    }

    public void handleDeath(Player player) {
        Player opposite = getOpposite(player);

        opposite.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 10F, 10F);

        opposite.hidePlayer(player);
        opposite.getInventory().clear();

        opposite.setHealth(20);
        opposite.getInventory().setArmorContents(null);

        end(opposite, player);
    }

    private void end(Player winner, Player loser) {
        Arrays.asList(winner, loser)
                .forEach(it -> {
                    it.sendMessage(ChatColor.GOLD + "Winner: " + ChatColor.YELLOW + winner.getName());
                    it.sendMessage(ChatColor.AQUA + "Inventories: " + ChatColor.GREEN + winner.getName() + ", " + ChatColor.RED + loser.getName());
                    it.sendMessage();
                });

        Bukkit.getScheduler().runTaskLater(Lux.getInstance(), () -> {
            Lobby.spawn(winner);
            Lobby.spawn(player);
        }, 50L);

        getArena().setAvailable(true);

        MatchHandler.INSTANCE.getMatches().remove(this);
    }

    private Player getOpposite(Player player) {
        return player == getPlayer() ? opponent : getPlayer();
    }
}
