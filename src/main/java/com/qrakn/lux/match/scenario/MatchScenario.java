package com.qrakn.lux.match.scenario;

import com.qrakn.lux.match.Match;
import com.qrakn.lux.match.participant.MatchParticipantGroup;

public interface MatchScenario {

    void onStart(Match match);

    void onEnd(Match match, MatchParticipantGroup winners, MatchParticipantGroup losers);
}
