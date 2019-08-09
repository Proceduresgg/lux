package com.qrakn.lux.match.task;

import com.qrakn.lux.Lux;
import com.qrakn.lux.match.impl.SinglesMatch;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class MatchStartTask extends BukkitRunnable {

    private static final Map<Integer, ChatColor> CHAT_COLORS = new HashMap<>();

    static {
        ChatColor[] colors = {
                ChatColor.GREEN, ChatColor.YELLOW,
                ChatColor.GOLD, ChatColor.RED, ChatColor.DARK_RED
        };

        IntStream.rangeClosed(0, colors.length).forEach(i -> CHAT_COLORS.put(i, colors[i]));
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
                match.getPlayer().sendMessage(CHAT_COLORS.get(count).toString() + ChatColor.BOLD + count + "...");
                match.getOpponent().sendMessage(CHAT_COLORS.get(count).toString() + ChatColor.BOLD + count + "...");
                break;
        }

        count--;
    }
}
