package com.qrakn.lux.match.ladder;

import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.match.kit.MatchKit;
import com.qrakn.lux.match.queue.MatchQueue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
public class Ladder {

    private final MatchQueue queue = new MatchQueue(this);

    private final String name;

    private int priority = 0;

    private ItemStack icon = new ItemStack(Material.DIAMOND);

    private MatchKit defaultKit = new MatchKit(null, null);

    public void save() {

    }

    public int getPlaying() {
        return MatchHandler.INSTANCE.getMatches()
                .stream()
                .filter(match -> match.getLadder() == this)
                .collect(Collectors.toList())
                .size();
    }

    public int getQueuing() {
        return queue.getQueue().size();
    }
}
