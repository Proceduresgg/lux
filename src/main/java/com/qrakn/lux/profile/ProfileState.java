package com.qrakn.lux.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileState {

    LOBBY(false, false, false, false, false, false, false),
    QUEUE(false, false, false, false, false, false, false),
    MATCH(true, true, true, false, false, false, false);

    private final boolean loseHunger, takeDamage, dealDamage, dropItem, pickupItem, breakBlock, placeBlock;
}
