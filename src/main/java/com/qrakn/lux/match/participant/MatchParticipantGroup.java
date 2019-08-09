package com.qrakn.lux.match.participant;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@Getter
public class MatchParticipantGroup {

    private final List<MatchParticipant> participants;

    public MatchParticipantGroup(MatchParticipant... participants) {
        this.participants = Arrays.asList(participants);
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
}
