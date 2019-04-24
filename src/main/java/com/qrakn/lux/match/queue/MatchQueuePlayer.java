package com.qrakn.lux.match.queue;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatchQueuePlayer {

    private final boolean ranked;

    public boolean canFight(MatchQueuePlayer opponent) {
        return true;
    }
}
