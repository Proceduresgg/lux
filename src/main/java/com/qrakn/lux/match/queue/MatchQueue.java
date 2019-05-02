package com.qrakn.lux.match.queue;

import com.qrakn.lux.Lux;
import com.qrakn.lux.match.ladder.MatchLadder;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class MatchQueue {

    private final static Map<MatchLadder, MatchQueue> queues = new HashMap<>();

    static {
        MatchLadder.getLadders().values()
                .forEach(ladder -> queues.put(ladder, new MatchQueue(ladder)));
    }

    private final Queue<MatchQueuePlayer> queue = new LinkedList<>();

    private final MatchLadder ladder;

    private MatchQueue(MatchLadder ladder) {
        this.ladder = ladder;

        start();
    }

    private void start() {
        Bukkit.getScheduler().runTaskTimer(Lux.getInstance(), () -> {
            while (queue.size() > 1) {
                MatchQueuePlayer player = queue.poll();
                MatchQueuePlayer opponent = queue.poll();
            }
        }, 0L , 10L);
    }

    public static void queue(Player player, MatchLadder ladder, boolean ranked) {
        Profile profile = Profile.getProfile(player);

        profile.setState(ProfileState.QUEUE);

        queues.get(ladder).queue.add(new MatchQueuePlayer(player, ranked));
    }
}
