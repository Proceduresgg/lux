package com.qrakn.lux.board;

import com.bizarrealex.aether.scoreboard.Board;
import com.bizarrealex.aether.scoreboard.BoardAdapter;
import com.bizarrealex.aether.scoreboard.cooldown.BoardCooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class LuxBoardAdapter implements BoardAdapter {
    @Override
    public String getTitle(Player player) {
        return ChatColor.GOLD.toString() + ChatColor.BOLD + "Festival " + ChatColor.WHITE + ChatColor.BOLD + "Practice";
    }

    @Override
    public List<String> getScoreboard(Player player, Board board, Set<BoardCooldown> set) {
        return Arrays.asList(
                ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------",
                ChatColor.WHITE + "Online: " + ChatColor.GOLD + Bukkit.getOnlinePlayers().size(),
                ChatColor.WHITE + "Playing: " + ChatColor.GOLD + Bukkit.getOnlinePlayers().size(),
                "",
                ChatColor.WHITE + "Global ELO: " + ChatColor.YELLOW + "1000",
                ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------"
        );
    }
}
