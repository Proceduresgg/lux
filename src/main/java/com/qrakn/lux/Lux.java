package com.qrakn.lux;

import co.aikar.commands.PaperCommandManager;
import com.bizarrealex.aether.Aether;
import com.qrakn.lux.board.LuxBoardAdapter;
import com.qrakn.lux.config.LuxConfig;
import com.qrakn.lux.lobby.LobbyListeners;
import com.qrakn.lux.match.MatchListeners;
import com.qrakn.lux.match.arena.command.ArenaCommand;
import com.qrakn.lux.match.arena.handler.ArenaHandler;
import com.qrakn.lux.match.handler.EnderpearlHandler;
import com.qrakn.lux.match.handler.ItemHandler;
import com.qrakn.lux.match.ladder.LadderCommand;
import com.qrakn.lux.match.ladder.handler.LadderHandler;
import com.qrakn.lux.match.queue.listener.MatchQueueListeners;
import com.qrakn.lux.match.spectator.SpectateCommand;
import com.qrakn.lux.match.spectator.SpectatorListener;
import com.qrakn.lux.mongo.MongoHandler;
import com.qrakn.lux.profile.ProfileListeners;
import com.qrakn.lux.profile.handler.ProfileHandler;
import com.qrakn.lux.world.WorldListeners;
import com.qrakn.phoenix.gui.PhoenixGui;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Random;

public class Lux extends JavaPlugin {

    @Getter
    private static Lux instance;

    public void onEnable() {
        instance = this;

        new PhoenixGui(this);
        new Aether(this, new LuxBoardAdapter());

        MongoHandler.INSTANCE.init();
        ArenaHandler.INSTANCE.init();
        EnderpearlHandler.INSTANCE.init();
        ItemHandler.INSTANCE.init();
        LadderHandler.INSTANCE.init();

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
        Arrays.asList(new LobbyListeners(), new ProfileListeners(), new WorldListeners(), new MatchListeners(),
                new MatchQueueListeners(), new SpectatorListener()).forEach(this::registerListener);
    }

    private void registerCommands(PaperCommandManager manager) {
        Arrays.asList(new LadderCommand(), new ArenaCommand(), new SpectateCommand()).forEach(manager::registerCommand);
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new LuxChunkGenerator();
    }

    public class LuxChunkGenerator extends ChunkGenerator {
        @Override
        public byte[] generate(World world, Random random, int x, int z) {
            return new byte[32768];
        }

        @Override
        public Location getFixedSpawnLocation(World world, Random random) {
            return world.getSpawnLocation();
        }
    }
}
