package com.qrakn.lux.match.queue;

import com.qrakn.lux.Lux;
import com.qrakn.lux.match.arena.handler.ArenaHandler;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.match.impl.SinglesMatch;
import com.qrakn.lux.match.ladder.Ladder;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
            while (queue.size() > 1) {
                MatchQueuePlayer player = queue.element();
                queue.stream()
                        .filter(it -> it != player)
                        .filter(it -> it.canFight(player))
                        .findFirst()
                        .ifPresent(opponent -> {
                            queue.remove(player);
                            queue.remove(opponent);

                            Bukkit.getScheduler().runTask(Lux.getInstance(), () -> {
                                MatchHandler.INSTANCE.getMatches()
                                        .add(new SinglesMatch(player.getPlayer(), opponent.getPlayer(), false, ladder,
                                                ArenaHandler.INSTANCE.getRandomArena()));
                            });
                        });
            }
        }, 0L, 10L);
    }
}
