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
import org.desp.vote.database.DailyVoteRepository;
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
            ItemStack midas = MMOItems.plugin.getItem("MISCELLANEOUS", "기타_미다스의손");
            if(activate){
                voteCoin.setAmount(2);
                ruby.setAmount(2);
                midas.setAmount(2);
            }
            DailyVoteRepository.getInstance().addDailyVote();
            List<ItemStack> items = new ArrayList<>();
            items.add(voteCoin);
            items.add(ruby);
            items.add(midas);
            MMOMail mmoMail = MMOMail.getInstance();
            Mail rewardMail = mmoMail.getMailAPI().createMail("시스템", "추천 보상입니다.", 0, items);
            mmoMail.getMailAPI().sendMail(username, rewardMail);
            Integer dailyVote = DailyVoteRepository.getInstance().getDailyVote();
            Bukkit.broadcastMessage("  §f" + username + "§a님께서 서버를 추천하여 보상을 지급받았습니다! §7§o(/추천) §f| §a오늘의 추천 횟수: §f"+dailyVote+"회 §7§o(매일 오후 9시에 당일 추천 수/8 만큼 30분간 추가 경험치 이벤트가 진행됩니다! 추천은 매일 오후 9시에 초기화됩니다.)");
            PlayerDataRepository.getInstance().setPlayerVoteTrue(offlinePlayer);
            return;
        }

        boolean activate = IDEPassAPI.getPlayer(player.getUniqueId().toString()).isActivate();
        ItemStack voteCoin = MMOItems.plugin.getItem("MISCELLANEOUS", "기타_추천코인");
        ItemStack ruby = MMOItems.plugin.getItem("CONSUMABLE", "기타_루비");
        ItemStack midas = MMOItems.plugin.getItem("MISCELLANEOUS", "기타_미다스의손");
        if (activate) {
            voteCoin.setAmount(2);
            ruby.setAmount(2);
            midas.setAmount(2);
        }
        DailyVoteRepository.getInstance().addDailyVote();
        List<ItemStack> items = new ArrayList<>();
        items.add(voteCoin);
        items.add(ruby);
        items.add(midas);
        Integer dailyVote = DailyVoteRepository.getInstance().getDailyVote();
        MMOMail mmoMail = MMOMail.getInstance();
        Mail rewardMail = mmoMail.getMailAPI().createMail("시스템", "추천 보상입니다.", 0, items);
        mmoMail.getMailAPI().sendMail(username, rewardMail);
        Bukkit.broadcastMessage("  §f" + username + "§a님께서 서버를 추천하여 보상을 지급받았습니다! §7§o(/추천) §f| §a오늘의 추천 횟수: §f"+dailyVote+"회 §7§o(매일 오후 9시에 당일 추천 수/8 만큼 30분간 추가 경험치 이벤트가 진행됩니다! 추천은 매일 오후 9시에 초기화됩니다.)");
        PlayerDataRepository.getInstance().setPlayerVoteTrue(player);


    }
}
