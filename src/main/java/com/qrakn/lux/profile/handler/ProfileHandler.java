package com.qrakn.lux.profile.handler;

import com.qrakn.lux.profile.Profile;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public enum ProfileHandler {

    INSTANCE;

    private final Map<UUID, Profile> profiles = new HashMap<>();

    public Profile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }

    public Profile getProfile(Player player) {
        return getProfile(player.getUniqueId());
    }

    public void saveProfiles() {
        profiles.values().forEach(Profile::save);
    }
}
