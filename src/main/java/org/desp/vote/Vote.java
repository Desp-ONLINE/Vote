package org.desp.vote;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.desp.vote.database.PlayerDataRepository;
import org.desp.vote.listener.PlayerJoinAndQuitListener;
import org.desp.vote.listener.VoteListener;

public final class Vote extends JavaPlugin {

    @Getter
    private static Vote instance;

    @Override
    public void onEnable() {
        instance = this;
        scheduleDailyReset();
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
}
