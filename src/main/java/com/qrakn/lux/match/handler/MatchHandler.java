package com.qrakn.lux.match.handler;

import com.qrakn.lux.match.Match;
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

    private final List<Match> matches = new ArrayList<>();

    public Optional<Match> get(Player player) {
        return matches
                .stream()
                .filter(match -> match.getOpponents().contains(player) || match.getTeam().contains(player))
                .findFirst();
    }

    public void add(Match match) {
        matches.add(match);
    }

    public void remove(Match match) {
        matches.remove(match);
    }
}
