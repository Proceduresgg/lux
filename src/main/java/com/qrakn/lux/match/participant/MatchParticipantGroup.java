package com.qrakn.lux.match.participant;

import com.qrakn.lux.match.handler.InventoryHandler;
import com.qrakn.lux.profile.handler.ProfileHandler;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MatchParticipantGroup {

    private final List<MatchParticipant> participants;

    public MatchParticipantGroup(Player... players) {
        this.participants = Arrays.stream(players)
                .map(player -> new MatchParticipant(ProfileHandler.INSTANCE.get(player)))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayers() {
        return participants
                .stream()
                .map(MatchParticipant::getPlayer)
                .collect(Collectors.toList());
    }

    public boolean contains(Player player) {
        return participants
                .stream()
                .map(participant -> participant.getProfile().getUuid())
                .anyMatch(uuid -> uuid == player.getUniqueId());
    }

    public boolean dead() {
       return participants
               .stream()
               .allMatch(MatchParticipant::isDead);
    }

    public void handleDeath(Player player) {
        InventoryHandler.INSTANCE.put(player);

        participants.stream().filter(participant -> participant.getProfile().getUuid() == player.getUniqueId())
                .findFirst()
                .ifPresent(participant -> participant.setDead(true));
    }
}
