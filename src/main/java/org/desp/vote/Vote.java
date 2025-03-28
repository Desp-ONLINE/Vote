package org.desp.vote;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
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
        schduleMonthlyReset();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinAndQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new VoteListener(), this);

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

    private void schduleMonthlyReset() {
        Bukkit.getScheduler().runTaskTimer(this, new BukkitRunnable() {
            @Override
            public void run() {
                LocalDate today = LocalDate.now();
                int currentMonth = today.getMonthValue();

                // 3월에서 4월로 넘어갔는지 확인
                if (lastCheckedMonth != currentMonth) {
                    PlayerDataRepository.getInstance().dropAllPlayerData(); // DB 드롭 실행
                }
                lastCheckedMonth = currentMonth;
            }
        }, 0L, 20L * 60); // 1분마다 실행
    }
}
