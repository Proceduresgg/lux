package com.qrakn.lux.match.spectator;

import com.qrakn.lux.match.Match;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Spectator {

    private final UUID uuid;

    private final Match match;
}
