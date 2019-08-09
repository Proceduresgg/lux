package com.qrakn.lux.match.handler;

import com.qrakn.lux.match.arena.Arena;
import com.qrakn.lux.match.impl.SinglesMatch;
import com.qrakn.lux.match.ladder.Ladder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum MatchHandler {

    INSTANCE;

    private final List<SinglesMatch> matches = new ArrayList<>();

    public Optional<SinglesMatch> get(Player player) {
        return matches.stream().filter(it -> it.getOpponent() == player || it.getPlayer() == player).findFirst();
    }
}
