package com.qrakn.lux.match.arena.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import com.qrakn.lux.match.arena.handler.ArenaHandler;
import com.qrakn.lux.match.arena.schematic.ArenaSchematic;
import com.qrakn.lux.match.arena.schematic.ArenaSchematicHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("arena")
public class ArenaCommand extends BaseCommand {

    @Subcommand("paste")
    public void onPaste(Player player, String arena) {
        ArenaSchematic schematic = ArenaSchematicHandler.INSTANCE.getSchematics().get(arena);

        ArenaHandler.INSTANCE.createArena(schematic);
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
}