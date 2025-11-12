package org.desp.vote.Command;

import com.binggre.mmomail.MMOMail;
import com.binggre.mmomail.objects.Mail;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.desp.vote.database.PlayerDataRepository;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VotePurchaseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;
        int playerMonthlyVoteCount = PlayerDataRepository.getInstance().getPlayerMonthlyVoteCount(player);
        if(strings.length == 0) {
            player.sendMessage("§c잘못된 명령어 사용법 입니다.");
            return false;
        }
        boolean isSuccessfullyConsumed;
        switch (strings[0]){
            case "태양석":
                isSuccessfullyConsumed = PlayerDataRepository.getInstance().consumePlayerMonthlyCount(player, 2);
                if(isSuccessfullyConsumed){
                    ItemStack resultItem = MMOItems.plugin.getItem("MISCELLANEOUS", "채집_태양석");
                    List<ItemStack> items = new ArrayList<>();
                    items.add(resultItem);
                    Mail mail = MMOMail.getInstance().getMailAPI().createMail("시스템", "추천 상점 구매 물품입니다.", 0, items);
                    MMOMail.getInstance().getMailAPI().sendMail(player.getName(), mail);
                    player.sendMessage("§a 성공적으로 태양석을 구매했습니다!");
                    System.out.println(player.getName() +" 님께서 태양석 추천상점에서 구매");
                }
                return true;
            case "청령의보주":
                isSuccessfullyConsumed = PlayerDataRepository.getInstance().consumePlayerMonthlyCount(player, 5);
                if(isSuccessfullyConsumed){
                    ItemStack resultItem = MMOItems.plugin.getItem("MISCELLANEOUS", "기타_청령의보주");
                    List<ItemStack> items = new ArrayList<>();
                    items.add(resultItem);
                    Mail mail = MMOMail.getInstance().getMailAPI().createMail("시스템", "추천 상점 구매 물품입니다.", 0, items);
                    MMOMail.getInstance().getMailAPI().sendMail(player.getName(), mail);
                    player.sendMessage("§a 성공적으로 청령의 보주를 구매했습니다!");
                    System.out.println(player.getName() +" 님께서 청령의 보주 추천상점에서 구매");
                }
                return true;
            case "오션크리스탈":
                isSuccessfullyConsumed = PlayerDataRepository.getInstance().consumePlayerMonthlyCount(player, 10);
                if(isSuccessfullyConsumed){
                    ItemStack resultItem = MMOItems.plugin.getItem("MISCELLANEOUS", "낚시_오션크리스탈");
                    List<ItemStack> items = new ArrayList<>();
                    items.add(resultItem);
                    Mail mail = MMOMail.getInstance().getMailAPI().createMail("시스템", "추천 상점 구매 물품입니다.", 0, items);
                    MMOMail.getInstance().getMailAPI().sendMail(player.getName(), mail);
                    player.sendMessage("§a 성공적으로 오션 크리스탈을 구매했습니다!");
                    System.out.println(player.getName() +" 님께서 오션 크리스탈 추천상점에서 구매");
                }
                return true;
            case "치장코인":
                isSuccessfullyConsumed = PlayerDataRepository.getInstance().consumePlayerMonthlyCount(player, 6);
                if(isSuccessfullyConsumed){
                    ItemStack resultItem = MMOItems.plugin.getItem("MISCELLANEOUS", "기타_치장코인");
                    List<ItemStack> items = new ArrayList<>();
                    items.add(resultItem);
                    Mail mail = MMOMail.getInstance().getMailAPI().createMail("시스템", "추천 상점 구매 물품입니다.", 0, items);
                    MMOMail.getInstance().getMailAPI().sendMail(player.getName(), mail);
                    player.sendMessage("§a 성공적으로 치장 코인을 구매했습니다!");
                    System.out.println(player.getName() +" 님께서 치장코인 추천상점에서 구매");
                }
                return true;
            case "80루비":
                isSuccessfullyConsumed = PlayerDataRepository.getInstance().consumePlayerMonthlyCount(player, 30);
                if(isSuccessfullyConsumed){
                    ItemStack resultItem = MMOItems.plugin.getItem("CONSUMABLE", "기타_루비10");
                    resultItem.setAmount(8);
                    List<ItemStack> items = new ArrayList<>();
                    items.add(resultItem);
                    Mail mail = MMOMail.getInstance().getMailAPI().createMail("시스템", "추천 상점 구매 물품입니다.", 0, items);
                    MMOMail.getInstance().getMailAPI().sendMail(player.getName(), mail);
                    player.sendMessage("§a 성공적으로 80 루비를 구매했습니다!");
                    System.out.println(player.getName() +" 님께서 80 루비 추천상점에서 구매");
                }
                return true;

        }

        return true;
    }


}
