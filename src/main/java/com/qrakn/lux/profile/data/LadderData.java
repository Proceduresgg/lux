package com.qrakn.lux.profile.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LadderData {

    private int elo = 1000;

    public int updateRating(int opponentRating, boolean winner) {
        int score = winner ? 1 : 0;
        double p1 = (1.0 / (1.0 + Math.pow(10.0, ((opponentRating - elo) / 400.0))));

        elo = (int) Math.ceil(elo + 32 * (score - p1));

        return elo;
    }
}
