package com.qrakn.lux.match.ladder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@RequiredArgsConstructor
public class MatchLadder {

    @Getter private final static Map<String, MatchLadder> ladders = new HashMap<>();

    private final String name;

    private ItemStack icon = new ItemStack(Material.DIAMOND);

    public static MatchLadder createLadder(String name) {
        MatchLadder ladder = new MatchLadder(name);

        ladders.put(name, ladder);

        return ladder;
    }

    public static void saveLadders() {

    }
}
