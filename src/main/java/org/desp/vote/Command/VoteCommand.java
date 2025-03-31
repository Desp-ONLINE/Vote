package org.desp.vote.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.desp.vote.database.PlayerDataRepository;
import org.jetbrains.annotations.NotNull;

public class VoteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        player.sendMessage("");
        player.sendMessage(" §a> §f아래 사이트에서 서버 추천 시 보상이 지급됩니다.");
        player.sendMessage(" §a> §fIDE 패스 활성화 시, 보상을 2배로 지급받습니다.");
        player.sendMessage(" §a> §fhttps://minelist.kr/servers/iderpg.kr");
        player.sendMessage(" ");
        player.sendMessage(" §a> §b현재 나의 월 추천 수: §f" + PlayerDataRepository.getInstance().getPlayerMonthlyVoteCount(player));
        player.sendMessage(" ");
        return false;
    }
}
