package com.qrakn.lux.match.task;

import com.qrakn.lux.Lux;
import com.qrakn.lux.match.impl.SinglesMatch;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class MatchStartTask extends BukkitRunnable {

    private static final Map<Integer, ChatColor> COUNT_COLORS = new HashMap<>();

    static {
        COUNT_COLORS.put(5, ChatColor.DARK_RED);
        COUNT_COLORS.put(4, ChatColor.RED);
        COUNT_COLORS.put(3, ChatColor.GOLD);
        COUNT_COLORS.put(2, ChatColor.YELLOW);
        COUNT_COLORS.put(1, ChatColor.GREEN);
    }

    private final SinglesMatch match;

    private int count = 5;

    public MatchStartTask(SinglesMatch match) {
        this.match = match;

        runTaskTimerAsynchronously(Lux.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        switch (count) {
            case 0:
                match.getPlayer().sendMessage(ChatColor.GREEN + "Start!");
                match.getOpponent().sendMessage(ChatColor.GREEN + "Start!");
                cancel();
                break;

            default:
                match.getPlayer().sendMessage( COUNT_COLORS.get(count).toString() + ChatColor.BOLD + count + "...");
                match.getOpponent().sendMessage(COUNT_COLORS.get(count).toString() + ChatColor.BOLD + count + "...");
                break;
        }

        count--;
    }
}
