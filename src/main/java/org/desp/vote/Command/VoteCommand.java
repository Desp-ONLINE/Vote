package org.desp.vote.Command;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.desp.vote.database.PlayerDataRepository;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class VoteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        TextComponent textComponent = new TextComponent("§a  §n클릭 시 월간 추천 보상 상점을 오픈할 수 있습니다.");
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§a클릭 시, 월간 추천 보상 상점을 오픈할 수 있습ㅈ니다! §7§o(/추천상점 명령어로도 오픈할 수 있습니다.)")));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/추천상점"));
        Player player = (Player) commandSender;
        player.sendMessage("");
        player.sendMessage(" §a> §f아래 사이트에서 서버 추천 시 보상이 지급됩니다.");
        player.sendMessage(" §a> §fIDE 패스 활성화 시, 보상을 2배로 지급받습니다.");
        player.sendMessage(" §a> §fhttps://minelist.kr/servers/iderpg.kr");
        player.sendMessage(" ");
        player.sendMessage(" §a> §b현재 나의 월 추천 수: §f" + PlayerDataRepository.getInstance().getPlayerMonthlyVoteCount(player) + " §7§o( 매월 1일 초기화되니 주의해주세요! )");
        player.sendMessage(" ");
        player.spigot().sendMessage(textComponent);
        player.sendMessage(" ");
        return false;
    }
}
