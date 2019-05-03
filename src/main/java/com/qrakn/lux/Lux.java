package com.qrakn.lux;

import co.aikar.commands.PaperCommandManager;
import com.qrakn.lux.lobby.LobbyListeners;
import com.qrakn.lux.match.ladder.MatchLadderCommand;
import com.qrakn.lux.match.ladder.handler.MatchLadderHandler;
import com.qrakn.lux.profile.ProfileListeners;
import com.qrakn.lux.profile.handler.ProfileHandler;
import com.qrakn.lux.world.WorldListeners;
import com.qrakn.phoenix.gui.PhoenixGui;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Lux extends JavaPlugin {

    @Getter
    private static Lux instance;

    public void onEnable() {
        instance = this;

        new PhoenixGui(this);

        registerListeners();
        registerCommands(new PaperCommandManager(this));
    }

    public void onDisable() {
        ProfileHandler.INSTANCE.saveProfiles();
        MatchLadderHandler.INSTANCE.saveLadders();
    }

    private void registerListeners() {
        Arrays.asList(new LobbyListeners(), new ProfileListeners(), new WorldListeners())
                .forEach(this::registerListener);
    }

    private void registerCommands(PaperCommandManager manager) {
        Arrays.asList(new MatchLadderCommand())
                .forEach(manager::registerCommand);
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }
}
