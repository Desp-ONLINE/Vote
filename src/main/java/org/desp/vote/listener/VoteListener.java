package org.desp.vote.listener;

import com.binggre.mmomail.MMOMail;
import com.binggre.mmomail.objects.Mail;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.desp.IDEPass.api.IDEPassAPI;
import org.desp.vote.database.PlayerDataRepository;

import java.util.ArrayList;
import java.util.List;

public class VoteListener implements Listener {

    @EventHandler
    public void playerVote(VotifierEvent event) {
        String username = event.getVote().getUsername();
        Player player = Bukkit.getPlayer(username);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);

        // 추천보상 지급부
        if (player == null) {
            boolean activate = IDEPassAPI.getPlayer(offlinePlayer);
            ItemStack voteCoin = MMOItems.plugin.getItem("MISCELLANEOUS", "기타_추천코인");
            ItemStack ruby = MMOItems.plugin.getItem("CONSUMABLE", "기타_루비");
            if(activate){
                voteCoin.setAmount(2);
                ruby.setAmount(2);
            }
            List<ItemStack> items = new ArrayList<>();
            items.add(voteCoin);
            items.add(ruby);
            MMOMail mmoMail = MMOMail.getInstance();
            Mail rewardMail = mmoMail.getMailAPI().createMail("시스템", "추천 보상입니다.", 0, items);
            mmoMail.getMailAPI().sendMail(username, rewardMail);
            Bukkit.broadcastMessage("  §f" + username + "§a님께서 서버를 추천하여 보상을 지급받았습니다! §7§o(/추천)");
            return;
        }

        boolean activate = IDEPassAPI.getPlayer(player.getUniqueId().toString()).isActivate();
        ItemStack voteCoin = MMOItems.plugin.getItem("MISCELLANEOUS", "기타_추천코인");
        ItemStack ruby = MMOItems.plugin.getItem("CONSUMABLE", "기타_루비");
        if (activate) {
            voteCoin.setAmount(2);
            ruby.setAmount(2);
        }
        List<ItemStack> items = new ArrayList<>();
        items.add(voteCoin);
        items.add(ruby);
        MMOMail mmoMail = MMOMail.getInstance();
        Mail rewardMail = mmoMail.getMailAPI().createMail("시스템", "추천 보상입니다.", 0, items);
        mmoMail.getMailAPI().sendMail(username, rewardMail);
        Bukkit.broadcastMessage("  §f" + username + "§a님께서 서버를 추천하여 보상을 지급받았습니다! §7§o(/추천)");
        // db 업로드부
        if(player == null) {
            offlinePlayer = Bukkit.getOfflinePlayer(username);
            PlayerDataRepository.getInstance().setPlayerVoteTrue(offlinePlayer);
            return;
        }
        PlayerDataRepository.getInstance().setPlayerVoteTrue(player);


    }
}
