package com.qrakn.lux.match.queue;

import com.qrakn.lux.Lux;
import com.qrakn.lux.match.Match;
import com.qrakn.lux.match.arena.handler.ArenaHandler;
import com.qrakn.lux.match.ladder.Ladder;
import com.qrakn.lux.match.participant.MatchParticipantGroup;
import com.qrakn.lux.match.scenario.impl.CasualMatchScenario;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

@Getter
public class MatchQueue {

    private final Queue<MatchQueuePlayer> queue = new LinkedList<>();

    private final Ladder ladder;

    public MatchQueue(Ladder ladder) {
        this.ladder = ladder;

        start();
    }

    private void start() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Lux.getInstance(), () -> {
            if (!queue.isEmpty()) {
                MatchQueuePlayer player = queue.element();

                queue.stream().filter(it -> it != player).filter(it -> it.canFight(player))
                        .findFirst()
                        .ifPresent(opponent -> {
                            removeQueuePlayer(player, opponent);

                            Bukkit.getScheduler().runTask(Lux.getInstance(), () -> {
                                ArenaHandler.INSTANCE.getRandomArena()
                                        .ifPresent(arena -> new Match(
                                                        new MatchParticipantGroup(player.getPlayer()),
                                                        new MatchParticipantGroup(opponent.getPlayer()),
                                                        ladder,
                                                        arena.load(),
                                                        new CasualMatchScenario()
                                                )
                                        );
                            });
                        });
            }
        }, 0L, 30L);
    }

    private void removeQueuePlayer(MatchQueuePlayer... players) {
        Arrays.stream(players).forEach(queue::remove);
    }
}
