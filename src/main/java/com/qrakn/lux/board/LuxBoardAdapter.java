package com.qrakn.lux.board;

import com.bizarrealex.aether.scoreboard.Board;
import com.bizarrealex.aether.scoreboard.BoardAdapter;
import com.bizarrealex.aether.scoreboard.cooldown.BoardCooldown;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.match.impl.SinglesMatch;
import com.qrakn.lux.match.MatchState;
import com.qrakn.lux.match.queue.handler.MatchQueueHandler;
import com.qrakn.lux.profile.Profile;
import com.qrakn.lux.profile.ProfileState;
import com.qrakn.lux.profile.handler.ProfileHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LuxBoardAdapter implements BoardAdapter {
    @Override
    public String getTitle(Player player) {
        return ChatColor.GOLD.toString() + ChatColor.BOLD + "Festival " + ChatColor.WHITE + ChatColor.BOLD + "Practice";
    }

    @Override
    public List<String> getScoreboard(Player player, Board board, Set<BoardCooldown> set) {
        Profile profile = ProfileHandler.INSTANCE.get(player);

        int playing = ProfileHandler.INSTANCE.getProfiles().values()
                .stream()
                .filter(it -> it.getState() == ProfileState.MATCH)
                .collect(Collectors.toList()).size();

        switch (profile.getState()) {
            case QUEUE:
                return Arrays.asList(
                        ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------",
                        ChatColor.GRAY + "Searching for match...",
                        " » " + ChatColor.AQUA + MatchQueueHandler.INSTANCE.getPlayers().get(player.getUniqueId()).getQueue().getLadder().getName(),
                        " » " + ChatColor.GREEN + "Casual " + ChatColor.GRAY + "(00:00)",
                        ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------"
                );

            case MATCH:
                SinglesMatch match = MatchHandler.INSTANCE.get(player);
                if (match == null || match.getState() == MatchState.ENDING) {
                    return new ArrayList<>();
                } else {
                    return Arrays.asList(
                            ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------",
                            ChatColor.WHITE + player.getName(),
                            ChatColor.GOLD + "vs",
                            ChatColor.WHITE + match.getOpposite(player).getName(),
                            ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------"
                    );
                }

            default:
                return Arrays.asList(
                        ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------",
                        ChatColor.WHITE + "Online: " + ChatColor.GOLD + Bukkit.getOnlinePlayers().size(),
                        ChatColor.WHITE + "Playing: " + ChatColor.GOLD + playing,
                        "",
                        ChatColor.WHITE + "Global ELO: " + ChatColor.YELLOW + "1000",
                        ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------"
                );
        }
    }
}
