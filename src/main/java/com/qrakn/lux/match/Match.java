package com.qrakn.lux.match;

import com.qrakn.lux.Lux;
import com.qrakn.lux.lobby.handler.LobbyHandler;
import com.qrakn.lux.match.arena.Arena;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.match.handler.InventoryHandler;
import com.qrakn.lux.match.ladder.Ladder;
import com.qrakn.lux.match.MatchState;
import com.qrakn.lux.match.participant.MatchParticipant;
import com.qrakn.lux.match.participant.MatchParticipantGroup;
import com.qrakn.lux.match.scenario.MatchScenario;
import com.qrakn.lux.match.spectator.handler.SpectatorHandler;
import com.qrakn.lux.match.task.MatchStartTask;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import com.qrakn.lux.util.PlayerUtils;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Match {

    private final MatchParticipantGroup team, opponents;

    private final Ladder ladder;

    private final Arena arena;

    private final MatchScenario[] scenarios;

    @Setter
    private MatchState state = MatchState.STARTING;

    public Match(MatchParticipantGroup team, MatchParticipantGroup opponents, Ladder ladder, Arena arena, MatchScenario... scenarios) {
        this.team = team;
        this.opponents = opponents;
        this.ladder = ladder;
        this.arena = arena;
        this.scenarios = scenarios;

        MatchHandler.INSTANCE.add(this);

        Arrays.stream(scenarios).forEach(scenario -> scenario.onStart(this));

        spawnPlayers();

        new MatchStartTask(this);
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>(team.getPlayers());

        players.addAll(opponents.getPlayers());

        return players;
    }

    private MatchParticipantGroup getOppositeGroup(Player player) {
        return team.contains(player) ? opponents : team;
    }

    private MatchParticipantGroup getGroup(Player player) {
        return team.contains(player) ? team : opponents;
    }

    private void end(MatchParticipantGroup winner, MatchParticipantGroup loser) {
        state = MatchState.ENDING;

        Arrays.stream(scenarios).forEach(scenario -> scenario.onEnd(this, winner, loser));

        Bukkit.getScheduler().runTaskLater(Lux.getInstance(), () -> winner.getPlayers().forEach(LobbyHandler.INSTANCE::spawn), 50L);

        SpectatorHandler.INSTANCE.handleMatch(this);

        arena.setAvailable(true);

        MatchHandler.INSTANCE.remove(this);
    }

    private void spawnPlayers() {
        getPlayers().forEach(player -> {
            ProfileHandler.INSTANCE.get(player).setState(ProfileState.MATCH);

            PlayerUtils.reset(player);

            ladder.getDefaultKit().apply(player);

            player.teleport(arena.getSpawns().get(team.contains(player) ? 0 : 1).toBukkitLocation());

            getPlayers().forEach(opponent -> PlayerUtils.setVisible(player, opponent));
        });
    }


    public void handleDeath(Player player) {
        getPlayers().forEach(opponent -> {
            opponent.hidePlayer(player);
            opponent.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 10F, 10F);
        });

        MatchParticipantGroup team = getGroup(player);

        team.handleDeath(player);

        if (team.dead()) {
            end(getOppositeGroup(player), team);
        }
    }
}
