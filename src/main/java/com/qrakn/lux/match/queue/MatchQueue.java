package com.qrakn.lux.match.queue;

import com.qrakn.lux.Lux;
import com.qrakn.lux.match.ladder.MatchLadder;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.LinkedList;
import java.util.Queue;

@Getter
public class MatchQueue {

    private final Queue<MatchQueuePlayer> queue = new LinkedList<>();

    private final MatchLadder ladder;

    public MatchQueue(MatchLadder ladder) {
        this.ladder = ladder;

        start();
    }

    private void start() {
        Bukkit.getScheduler().runTaskTimer(Lux.getInstance(), () -> {
            while (queue.size() > 1) {
                MatchQueuePlayer player = queue.poll();
                MatchQueuePlayer opponent = queue.poll();
            }
        }, 0L, 10L);
    }
}
