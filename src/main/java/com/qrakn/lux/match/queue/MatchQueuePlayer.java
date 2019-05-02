package com.qrakn.lux.match.queue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public class MatchQueuePlayer {

    private final Player player;

    private final boolean ranked;

    public boolean canFight(MatchQueuePlayer opponent) {
        return true;
    }
}
