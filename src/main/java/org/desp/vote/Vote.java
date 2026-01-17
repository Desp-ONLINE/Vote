package org.desp.vote;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.desp.vote.Command.VoteCommand;
import org.desp.vote.Command.VotePurchaseCommand;
import org.desp.vote.Command.VoteShopCommand;
import org.desp.vote.database.DailyVoteRepository;
import org.desp.vote.database.PlayerDataRepository;
import org.desp.vote.listener.PlayerJoinAndQuitListener;
import org.desp.vote.listener.VoteListener;

public final class Vote extends JavaPlugin {

    @Getter
    private static Vote instance;

    private int lastCheckedMonth = 0;


    @Override
    public void onEnable() {

        instance = this;
        LocalDate now = LocalDate.now();
        lastCheckedMonth = now.getMonthValue();
        scheduleDailyReset();
        minuteScheduler();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinAndQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new VoteListener(), this);
        getCommand("추천").setExecutor(new VoteCommand());
        getCommand("추천구매").setExecutor(new VotePurchaseCommand());
        getCommand("추천상점").setExecutor(new VoteShopCommand());
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!PlayerDataRepository.getInstance().playerExists(onlinePlayer)) {
                PlayerDataRepository.getInstance().insertDefaultPlayerData(onlinePlayer);
            }
        }
    }

    @Override
    public void onDisable() {
    }

    private void scheduleDailyReset() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0); // 12시
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long delay = calendar.getTimeInMillis() - System.currentTimeMillis();
        if (delay < 0) {
            delay += 86400000; // 다음 날 12시로 설정
        }
        // 자정마다 실행
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Bukkit.getScheduler().runTaskAsynchronously(Vote.this, () -> {
                    PlayerDataRepository.getInstance().resetPlayerVote();

                });
            }
        }, delay, 86400000); // 매일 실행
    }

    private void minuteScheduler() {
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                LocalTime now = LocalTime.now();
                if(now.getHour() == 9 && now.getMinute() == 0){
                    Integer dailyVote = DailyVoteRepository.getInstance().getDailyVote();
                    if(LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY || LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY){
                        int voteAmount = dailyVote / 5;
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "idemanager channelcommand true 경험치이벤트 "+voteAmount+" 1800");
                        DailyVoteRepository.getInstance().resetDailyVote();
                        return;
                    }
                    int voteAmount = dailyVote / 7;
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "idemanager channelcommand true 경험치이벤트 "+voteAmount+" 1800");
                    DailyVoteRepository.getInstance().resetDailyVote();
                }
            }
        }, 0L, 20L * 30);
    }


}
