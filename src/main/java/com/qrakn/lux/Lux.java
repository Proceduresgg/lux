package com.qrakn.lux;

import co.aikar.commands.PaperCommandManager;
import com.bizarrealex.aether.Aether;
import com.qrakn.lux.board.LuxBoardAdapter;
import com.qrakn.lux.config.LuxConfig;
import com.qrakn.lux.lobby.LobbyListeners;
import com.qrakn.lux.match.MatchListeners;
import com.qrakn.lux.match.arena.command.ArenaCommand;
import com.qrakn.lux.match.arena.handler.ArenaHandler;
import com.qrakn.lux.match.ladder.LadderCommand;
import com.qrakn.lux.match.ladder.handler.LadderHandler;
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
        new Aether(this, new LuxBoardAdapter());

        ArenaHandler.INSTANCE.init();

        registerListeners();
        registerCommands(new PaperCommandManager(this));
    }

    public void onDisable() {
        LuxConfig.save();

        ProfileHandler.INSTANCE.save();
        LadderHandler.INSTANCE.save();
        ArenaHandler.INSTANCE.save();
    }

    private void registerListeners() {
        Arrays.asList(new LobbyListeners(), new ProfileListeners(), new WorldListeners(), new MatchListeners())
                .forEach(this::registerListener);
    }

    private void registerCommands(PaperCommandManager manager) {
        Arrays.asList(new LadderCommand(), new ArenaCommand())
                .forEach(manager::registerCommand);
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }
}
