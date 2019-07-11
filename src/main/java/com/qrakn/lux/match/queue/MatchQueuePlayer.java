package com.qrakn.lux.match.queue;

import com.qrakn.lux.lobby.Lobby;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
        return true;
    }

    public void exit() {
        this.queue.getQueue().remove(this);

        ProfileHandler.INSTANCE.getProfile(player).setState(ProfileState.LOBBY);

        Lobby.applySpawnContents(player);
    }
}
