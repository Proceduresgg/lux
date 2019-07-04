package com.qrakn.lux.match.ladder;

import com.qrakn.lux.match.queue.MatchQueue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@RequiredArgsConstructor
public class Ladder {

    private final MatchQueue queue = new MatchQueue(this);

    private final String name;

    private int priority = 0;

    private ItemStack icon = new ItemStack(Material.DIAMOND);

    public void save() {

    }
}
