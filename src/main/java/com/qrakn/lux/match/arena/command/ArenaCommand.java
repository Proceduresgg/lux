package com.qrakn.lux.match.arena.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import com.qrakn.lux.Lux;
import com.qrakn.lux.match.arena.Arena;
import com.qrakn.lux.match.arena.handler.ArenaHandler;
import com.qrakn.lux.match.arena.schematic.ArenaSchematic;
import com.qrakn.lux.match.arena.schematic.ArenaSchematicHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@CommandAlias("arena")
public class ArenaCommand extends BaseCommand {

    private Executor executor = Executors.newSingleThreadExecutor();

    @Subcommand("paste")
    public void onPaste(Player player, String arena) {
        ArenaSchematic schematic = ArenaSchematicHandler.INSTANCE.getSchematics().get(arena);

        executor.execute(() -> {
            Bukkit.getScheduler().runTask(Lux.getInstance(), () -> {
                ArenaHandler.INSTANCE.createArena(schematic);
            });
        });
    }

    @Subcommand("model")
    public void onModel(Player player, String arena) {
        ArenaSchematicHandler.INSTANCE.getSchematics().keySet().forEach(System.out::println);
        ArenaSchematic schematic = ArenaSchematicHandler.INSTANCE.getSchematics().get(arena);

        if (schematic == null) {
            Bukkit.getLogger().info("Schematic null");
        } else if (schematic.getModelArena() == null) {
            Bukkit.getLogger().info("Model Arena Null");
        } else if (schematic.getModelArena().getLocation() == null) {
            Bukkit.getLogger().info("SKRT");
        }

        player.teleport(schematic.getModelArena().getLocation());
    }

    @Subcommand("spawn1")
    public void onSpawn1(Player player, String arena) {
        Optional<Arena> optional = ArenaHandler.INSTANCE.getGrid().values()
                .stream()
                .filter(it -> !it.isModelArena())
                .filter(it -> it.getSchematic() == ArenaSchematicHandler.INSTANCE.getSchematics().get(arena))
                .findFirst();

        optional.ifPresent(arena1 -> player.teleport(arena1.getSpawns().get(0)));
    }

    @Subcommand("spawn2")
    public void onSpawn2(Player player, String arena) {
        Optional<Arena> optional = ArenaHandler.INSTANCE.getGrid().values()
                .stream()
                .filter(it -> !it.isModelArena())
                .filter(it -> it.getSchematic() == ArenaSchematicHandler.INSTANCE.getSchematics().get(arena))
                .findFirst();

        optional.ifPresent(arena1 -> player.teleport(arena1.getSpawns().get(1)));
    }
}
