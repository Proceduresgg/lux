package com.qrakn.lux.match.participant;

import com.qrakn.lux.profile.Profile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class MatchParticipant {

    private final List<Double> healthIntensityList = new ArrayList<>();

    private final Profile profile;

    private int hits, health;

    private boolean dead = false;
}
