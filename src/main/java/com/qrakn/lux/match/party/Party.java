package com.qrakn.lux.match.party;

import com.qrakn.lux.lobby.handler.LobbyHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Party {

    private final List<UUID> players = new ArrayList<>();

    private final UUID leader;

    public void remove(Player player) {
        LobbyHandler.INSTANCE.spawn(player);

        players.remove(player.getUniqueId());

        players.stream().map(Bukkit::getPlayer).forEach(member -> member.sendMessage(player.getName() + " has " + "left the party."));

        player.sendMessage("You left the party.");
    }
}
