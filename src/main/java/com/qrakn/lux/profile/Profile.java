package com.qrakn.lux.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class Profile {

    private final UUID uuid;
    private final String name;

    private ProfileState state = ProfileState.LOBBY;

    public Profile load() {
        return this;
    }

    public void save() {
    }
}
