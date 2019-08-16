package com.qrakn.lux.match.queue;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class MatchQueuePlayer {

    private final Player player;

    private final MatchQueue queue;

    private final boolean ranked;

    public MatchQueuePlayer(Player player, MatchQueue queue, boolean ranked) {
        this.player = player;
        this.queue = queue;
        this.ranked = ranked;

        queue.getQueue().add(this);
    }

    public boolean canFight(MatchQueuePlayer opponent) {
        return ranked == opponent.isRanked();
    }
}
