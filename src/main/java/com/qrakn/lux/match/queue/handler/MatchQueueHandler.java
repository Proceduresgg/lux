package com.qrakn.lux.match.queue.handler;

import com.qrakn.lux.match.ladder.Ladder;
import com.qrakn.lux.match.ladder.handler.LadderHandler;
import com.qrakn.lux.match.queue.MatchQueue;
import com.qrakn.lux.match.queue.MatchQueuePlayer;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum MatchQueueHandler {

    INSTANCE;

    private final Map<Ladder, MatchQueue> queues = new HashMap<>();

    public void init() {
        LadderHandler.INSTANCE.getLadders().values()
                .forEach(ladder -> queues.put(ladder, new MatchQueue(ladder)));
    }

    public void queue(Player player, Ladder ladder, boolean ranked) {
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        profile.setState(ProfileState.QUEUE);

        queues.get(ladder).getQueue().add(new MatchQueuePlayer(player, ranked));
    }
}
