package com.qrakn.lux.match.queue.handler;

import com.qrakn.lux.Lux;
import com.qrakn.lux.match.ladder.Ladder;
import com.qrakn.lux.match.ladder.handler.LadderHandler;
import com.qrakn.lux.match.queue.MatchQueue;
import com.qrakn.lux.match.queue.MatchQueuePlayer;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum MatchQueueHandler {

    INSTANCE;

    public void queue(Player player, Ladder ladder, boolean ranked) {
        Profile profile = ProfileHandler.INSTANCE.getProfile(player);

        profile.setState(ProfileState.QUEUE);

        ladder.getQueue().getQueue().add(new MatchQueuePlayer(player, ranked));
    }
}
