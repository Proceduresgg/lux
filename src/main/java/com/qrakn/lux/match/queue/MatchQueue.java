package com.qrakn.lux.match.queue;

import com.qrakn.lux.match.ladder.MatchLadder;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
public class MatchQueue {

    private final static Map<MatchLadder, MatchQueue> queues = new HashMap<>();

    static {
        MatchLadder.getLadders().values()
                .forEach(ladder -> queues.put(ladder, new MatchQueue(ladder)));
    }

    private final List<MatchQueuePlayer> queue = new LinkedList<>();

    private final MatchLadder ladder;

    private MatchQueue(MatchLadder ladder) {
        this.ladder = ladder;

        start();
    }

    private void start() {

    }

    public static void queue(Player player, MatchLadder ladder, boolean ranked) {

    }
}
