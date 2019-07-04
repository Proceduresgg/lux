package com.qrakn.lux.match.queue;

import com.qrakn.lux.Lux;
import com.qrakn.lux.lobby.Lobby;
import com.qrakn.lux.match.ladder.Ladder;
import lombok.Getter;
import org.bukkit.Bukkit;

import javax.persistence.Lob;
import java.util.*;

@Getter
public class MatchQueue {

    private final List<MatchQueuePlayer> queue = new ArrayList<>();

    private final Ladder ladder;

    public MatchQueue(Ladder ladder) {
        this.ladder = ladder;

        start();
    }

    private void start() {
        Bukkit.getScheduler().runTaskTimer(Lux.getInstance(), () -> {
            while (queue.size() > 1) {
                MatchQueuePlayer player = queue.get(0);
                Optional<MatchQueuePlayer> optional = queue.stream().filter(it -> it.canFight(player)).findFirst();

                if (optional.isPresent()) {
                    MatchQueuePlayer opponent = optional.get();

                    queue.remove(player);
                    queue.remove(opponent);

                    Lobby.spawn(player.getPlayer());
                    Lobby.spawn(opponent.getPlayer());
                }
            }
        }, 0L, 10L);
    }
}
