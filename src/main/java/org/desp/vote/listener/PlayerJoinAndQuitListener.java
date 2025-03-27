package org.desp.vote.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.desp.vote.database.PlayerDataRepository;

public class PlayerJoinAndQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerDataRepository playerDataRepository = PlayerDataRepository.getInstance();

        if (!playerDataRepository.playerExists(player)) {
            playerDataRepository.insertDefaultPlayerData(player);
        }
        if(!playerDataRepository.isPlayerVoteToday(player)) {
            player.sendTitle("§a/추천 §f이 가능합니다!", "§e서버를 추천하여 강력한 보상을 수령하세요!", 10, 30, 10);
            player.sendMessage("§e  오늘 §a/추천 §e하지 않으셨습니다! 서버를 추천하여 강력한 보상을 수령하세요!");
        }
    }
}
