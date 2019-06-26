package com.qrakn.lux.match.handler;

import com.qrakn.lux.match.Match;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum MatchHandler {

    INSTANCE;

    private final List<Match> matches = new ArrayList<>();

    public Match getMatch(Player player) {
        return null;
    }
}
