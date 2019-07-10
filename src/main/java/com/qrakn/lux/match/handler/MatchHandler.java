package com.qrakn.lux.match.handler;

import com.qrakn.lux.match.Match;
import com.qrakn.lux.match.impl.SinglesMatch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum MatchHandler {

    INSTANCE;

    private final List<SinglesMatch> matches = new ArrayList<>();

    public SinglesMatch getMatch(Player player) {
        return matches
                .stream()
                .filter(it -> it.getOpponent() == player || it.getPlayer() == player)
                .findFirst().get();
    }
}
