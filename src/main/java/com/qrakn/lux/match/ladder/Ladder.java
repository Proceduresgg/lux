package com.qrakn.lux.match.ladder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@RequiredArgsConstructor
public class Ladder {

    private final String name;

    private ItemStack icon = new ItemStack(Material.DIAMOND);

    public void save() {

    }
}
