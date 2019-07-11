package com.qrakn.lux.util;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerUtils {

    public static void reset(Player player) {
        player.setGameMode(GameMode.SURVIVAL);

        player.setFlying(false);
        player.setAllowFlight(false);
        player.setCanPickupItems(false);

        player.setHealth(20);
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.setFallDistance(0);
        player.setTotalExperience(0);
        player.setMaximumNoDamageTicks(20);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getActivePotionEffects()
                .stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect);
    }
}
