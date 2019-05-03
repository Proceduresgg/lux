package com.qrakn.lux.match.ladder.handler;

import com.qrakn.lux.match.ladder.MatchLadder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum MatchLadderHandler {

    INSTANCE;

    private final Map<String, MatchLadder> ladders = new HashMap<>();

    public void init() {

    }

    public void saveLadders() {
        ladders.values().forEach(MatchLadder::save);
    }

    public MatchLadder createLadder(String name) {
        MatchLadder ladder = new MatchLadder(name);

        ladders.put(name, ladder);

        return ladder;
    }
}
