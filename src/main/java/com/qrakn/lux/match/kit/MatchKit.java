package com.qrakn.lux.match.kit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public class MatchKit {

    private final ItemStack[] contents, armor;

    public void apply(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(armor);
        player.getInventory().setContents(contents);
        player.updateInventory();
    }
}
