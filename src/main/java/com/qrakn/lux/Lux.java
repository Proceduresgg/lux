package com.qrakn.lux;

import com.qrakn.lux.lobby.LobbyListeners;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileListeners;
import com.qrakn.phoenix.gui.PhoenixGui;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Lux extends JavaPlugin {

    @Getter private static Lux instance;

    public void onEnable() {
        instance = this;

        new PhoenixGui(this); // Initializing the Menu API

        Arrays.asList(new LobbyListeners(), new ProfileListeners())
                .forEach(this::registerListener);
    }

    public void onDisable() {
        Profile.save();
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }
}
