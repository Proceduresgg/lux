package com.qrakn.lux.lobby;

import com.qrakn.lux.config.LuxConfiguration;
import com.qrakn.lux.util.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LobbyListeners implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Lobby.spawn(player);

        LuxConfiguration.getMessages().getConfig().getStringList("join-messages")
                .forEach(message -> player.sendMessage(MessageUtils.color(message)));
    }
}
