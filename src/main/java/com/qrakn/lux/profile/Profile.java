package com.qrakn.lux.profile;

import com.qrakn.lux.match.ladder.Ladder;
import com.qrakn.lux.profile.data.LadderData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@RequiredArgsConstructor
public class Profile {

    private final Map<Ladder, LadderData> ladderData = new HashMap<>();

    private final UUID uuid;

    private final String name;

    private ProfileState state = ProfileState.LOBBY;

    public CompletableFuture<Profile> load() {
        return null;
    }

    public int getRating(Ladder ladder) {
        return ladderData.computeIfAbsent(ladder, k -> new LadderData()).getElo();
    }
    public void save() {
    }
}
