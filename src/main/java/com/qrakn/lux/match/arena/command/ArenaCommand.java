package com.qrakn.lux.match.arena.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import com.qrakn.lux.match.arena.handler.ArenaHandler;
import com.qrakn.lux.match.arena.schematic.ArenaSchematic;
import com.qrakn.lux.match.arena.schematic.ArenaSchematicHandler;
import org.bukkit.entity.Player;

@CommandAlias("arena")
public class ArenaCommand extends BaseCommand {

    @Subcommand("paste")
    public void onPaste(Player player, String arena, int amount) {
        ArenaSchematic schematic = ArenaSchematicHandler.INSTANCE.getSchematics().get(arena);

        ArenaHandler.INSTANCE.createArena(schematic);
    }
}
