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
            case "판도라의열쇠":
                isSuccessfullyConsumed = PlayerDataRepository.getInstance().consumePlayerMonthlyCount(player, 3);
                if(isSuccessfullyConsumed){
                    ItemStack resultItem = MMOItems.plugin.getItem("MISCELLANEOUS", "기타_판도라의열쇠");
                    List<ItemStack> items = new ArrayList<>();
                    items.add(resultItem);
                    Mail mail = MMOMail.getInstance().getMailAPI().createMail("시스템", "추천 상점 구매 물품입니다.", 0, items);
                    MMOMail.getInstance().getMailAPI().sendMail(player.getName(), mail);
                    player.sendMessage("§a 성공적으로 판도라의 열쇠를 구매했습니다!");
                    System.out.println(player.getName() +" 님께서 판도라의 열쇠 추천상점에서 구매");
                }
                return true;

            case "수호의빛":
                isSuccessfullyConsumed = PlayerDataRepository.getInstance().consumePlayerMonthlyCount(player, 10);
                if(isSuccessfullyConsumed){
                    ItemStack resultItem = MMOItems.plugin.getItem("MISCELLANEOUS", "기타_파괴방지권");
                    List<ItemStack> items = new ArrayList<>();
                    items.add(resultItem);
                    Mail mail = MMOMail.getInstance().getMailAPI().createMail("시스템", "추천 상점 구매 물품입니다.", 0, items);
                    MMOMail.getInstance().getMailAPI().sendMail(player.getName(), mail);
                    player.sendMessage("§a 성공적으로 수호의 빛을 구매했습니다!");
                    System.out.println(player.getName() +" 님께서 수호의 빛 추천상점에서 구매");

                }
                return true;
            case "레전더리열쇠":
                isSuccessfullyConsumed = PlayerDataRepository.getInstance().consumePlayerMonthlyCount(player, 14);
                if(isSuccessfullyConsumed){
                    ItemStack resultItem = MMOItems.plugin.getItem("MISCELLANEOUS", "기타_판도라의열쇠_레전더리");
                    List<ItemStack> items = new ArrayList<>();
                    items.add(resultItem);
                    Mail mail = MMOMail.getInstance().getMailAPI().createMail("시스템", "추천 상점 구매 물품입니다.", 0, items);
                    MMOMail.getInstance().getMailAPI().sendMail(player.getName(), mail);
                    player.sendMessage("§a 성공적으로 판도라의 열쇠 (레전더리)를 구매했습니다!");
                    System.out.println(player.getName() +" 님께서 레전더리 열쇠 추천상점에서 구매");
                }
                return true;
            case "50루비":
                isSuccessfullyConsumed = PlayerDataRepository.getInstance().consumePlayerMonthlyCount(player, 30);
                if(isSuccessfullyConsumed){
                    ItemStack resultItem = MMOItems.plugin.getItem("CONSUMABLE", "기타_루비50");
                    List<ItemStack> items = new ArrayList<>();
                    items.add(resultItem);
                    Mail mail = MMOMail.getInstance().getMailAPI().createMail("시스템", "추천 상점 구매 물품입니다.", 0, items);
                    MMOMail.getInstance().getMailAPI().sendMail(player.getName(), mail);
                    player.sendMessage("§a 성공적으로 50 루비를 구매했습니다!");
                    System.out.println(player.getName() +" 님께서 50 루비 추천상점에서 구매");
                }
                return true;

        }

        return true;
    }


}
