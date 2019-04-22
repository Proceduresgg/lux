package com.qrakn.lux.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Profile {

    @Getter private static Map<UUID, Profile> profiles = new HashMap<>();

    private final UUID identifier;
    private final String name;

    public Profile load() {
        return this;
    }

    public static void save() {

    }
}
