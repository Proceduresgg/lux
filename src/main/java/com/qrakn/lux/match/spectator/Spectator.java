package com.qrakn.lux.match.spectator;

import com.qrakn.lux.lobby.Lobby;
import com.qrakn.lux.match.impl.SinglesMatch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Spectator {

    private final UUID uuid;

    private final SinglesMatch match;
}
