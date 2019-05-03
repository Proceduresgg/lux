package com.qrakn.lux.match.queue.handler;

import com.qrakn.lux.match.ladder.MatchLadder;
import com.qrakn.lux.match.ladder.handler.MatchLadderHandler;
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

    private final Map<MatchLadder, MatchQueue> queues = new HashMap<>();

    public void init() {
        MatchLadderHandler.INSTANCE.getLadders().values()
                .forEach(ladder -> queues.put(ladder, new MatchQueue(ladder)));
    }

    public void queue(Player player, MatchLadder ladder, boolean ranked) {
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        profile.setState(ProfileState.QUEUE);

        queues.get(ladder).getQueue().add(new MatchQueuePlayer(player, ranked));
    }
}
