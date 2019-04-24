package com.qrakn.lux.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
@RequiredArgsConstructor
public class Profile {

    @Getter private final static Map<UUID, Profile> profiles = new HashMap<>();

    private final UUID uuid;
    private final String name;

    private ProfileState state = ProfileState.LOBBY;

    public Profile load() {
        return this;
    }

    public void save() {}

    public static void saveProfiles() {
        profiles.values().forEach(Profile::save);
    }

    public static Profile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }

    public static Profile getProfile(Player player) {
        return getProfile(player.getUniqueId());
    }
}
