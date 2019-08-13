package com.qrakn.lux.match.task;

import com.qrakn.lux.Lux;
import com.qrakn.lux.match.Match;
import com.qrakn.lux.match.MatchState;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class MatchStartTask extends BukkitRunnable {

    private static final Map<Integer, ChatColor> CHAT_COLORS = new HashMap<>(); // for different countdown colors

    static {
        ChatColor[] colors = {
                ChatColor.GREEN, ChatColor.YELLOW,
                ChatColor.GOLD, ChatColor.RED, ChatColor.DARK_RED
        };

        IntStream.rangeClosed(1, colors.length).forEach(i -> CHAT_COLORS.put(i, colors[i - 1]));
    }

    private final Match match;

    private int count = 5;

    public MatchStartTask(Match match) {
        this.match = match;

        runTaskTimerAsynchronously(Lux.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        if (match.getState() == MatchState.ENDING) {
            cancel();
            return;
        }

        switch (count) {
            case 0:
                match.setState(MatchState.FIGHTING);
                match.getPlayers().forEach(player -> player.sendMessage(ChatColor.GREEN + "Start!"));

                cancel();
                return;

            default:
                match.getPlayers().forEach(player -> player.sendMessage(CHAT_COLORS.get(count).toString() + ChatColor.BOLD + count + "..."));

                count--;
                break;
        }
    }
}
