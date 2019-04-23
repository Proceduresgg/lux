package com.qrakn.lux.match.queue;

import com.qrakn.lux.match.ladder.MatchLadder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    private void start() {}
}
