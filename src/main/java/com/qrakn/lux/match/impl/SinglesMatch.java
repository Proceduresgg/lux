package com.qrakn.lux.match.impl;

import com.qrakn.lux.Lux;
import com.qrakn.lux.lobby.handler.LobbyHandler;
import com.qrakn.lux.match.Match;
import com.qrakn.lux.match.arena.Arena;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.match.handler.InventoryHandler;
import com.qrakn.lux.match.ladder.Ladder;
import com.qrakn.lux.match.MatchState;
import com.qrakn.lux.match.spectator.handler.SpectatorHandler;
import com.qrakn.lux.match.task.MatchStartTask;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import com.qrakn.lux.util.PlayerUtils;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class SinglesMatch extends Match {

    private final Player player, opponent;

    public SinglesMatch(Player player, Player opponent, boolean competitive, Ladder ladder, Arena arena) {
        super(competitive, ladder, arena);

        this.player = player;
        this.opponent = opponent;

        arena.setAvailable(false);

        start();
    }

    private void start() {
        player.teleport(getArena().getSpawns().get(0).toBukkitLocation());
        opponent.teleport(getArena().getSpawns().get(1).toBukkitLocation());

        player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Opponent: " + ChatColor.YELLOW + opponent.getName());
        opponent.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Opponent: " + ChatColor.YELLOW + player.getName());

        Profile playerProfile = ProfileHandler.INSTANCE.get(player);
        Profile opponentProfile = ProfileHandler.INSTANCE.get(opponent);

        playerProfile.setState(ProfileState.MATCH);
        opponentProfile.setState(ProfileState.MATCH);

        getLadder().getDefaultKit().apply(player);
        getLadder().getDefaultKit().apply(opponent);

        player.showPlayer(opponent);
        opponent.showPlayer(player);

        new MatchStartTask(this);

        setState(MatchState.FIGHTING);
    }

    public void handleDeath(Player player) {
        Player opposite = getOpposite(player);

        InventoryHandler.INSTANCE.put(player);
        InventoryHandler.INSTANCE.put(opposite);

        opposite.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 10F, 10F);

        opposite.hidePlayer(player);

        PlayerUtils.reset(opposite);

        end(opposite, player);
    }

    private void end(Player winner, Player loser) {
        setState(MatchState.ENDING);

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

        Bukkit.getScheduler().runTaskLater(Lux.getInstance(), () -> {
            LobbyHandler.INSTANCE.spawn(winner);
        }, 50L);

        new ArrayList<>(SpectatorHandler.INSTANCE.getSpectators().values())
                .stream()
                .filter(spectator -> spectator.getMatch() == this)
                .forEach(spectator -> SpectatorHandler.INSTANCE.remove(Bukkit.getPlayer(spectator.getUuid())));

        getArena().setAvailable(true);

        MatchHandler.INSTANCE.getMatches().remove(this);
    }

    public Player getOpposite(Player player) {
        return player == getPlayer() ? opponent : getPlayer();
    }
}
