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

    public Profile get(Player player) {
        return profiles.computeIfAbsent(player.getUniqueId(), c -> new Profile(player.getUniqueId(), player.getName()));
    }

    public void put(Profile profile) {
        profiles.put(profile.getUuid(), profile);
    }

    public void remove(Player player) {
        profiles.remove(player.getUniqueId());
    }

    public void save() {
        profiles.values().forEach(Profile::save);
    }
}
