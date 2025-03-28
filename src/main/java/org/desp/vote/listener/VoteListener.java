package org.desp.vote.listener;

import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.desp.vote.database.PlayerDataRepository;

public class VoteListener implements Listener {

    @EventHandler
    public void playerVote(VotifierEvent event) {
        String username = event.getVote().getUsername();
        Player player = Bukkit.getPlayer(username);
        if(player == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
            PlayerDataRepository.getInstance().setPlayerVoteTrue(offlinePlayer);
            return;
        }
        PlayerDataRepository.getInstance().setPlayerVoteTrue(player);
        int playerVoteStreak = PlayerDataRepository.getInstance().getPlayerVoteStreak(player);
        if(playerVoteStreak == 3) {

        }

    }
}
