package com.qrakn.lux.match;

import com.qrakn.lux.match.arena.Arena;
import com.qrakn.lux.match.ladder.Ladder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Match {

    private final boolean competitive;

    private final Ladder ladder;

    private final Arena arena;

    private MatchState state = MatchState.STARTING;
}
