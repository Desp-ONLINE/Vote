package org.desp.vote.database;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.desp.vote.DTO.StreakRewardDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StreakRewardRepository {

    private static StreakRewardRepository instance;
    private final MongoCollection<Document> rewardCollection;
    public HashMap<Integer, List<StreakRewardDto>> rewardsCache = new HashMap<>();


    public StreakRewardRepository() {
        DatabaseRegister database = new DatabaseRegister();
        this.rewardCollection = database.getDatabase().getCollection("StreakRewardData");
    }

    public static StreakRewardRepository getInstance() {
        if (instance == null) {
            instance = new StreakRewardRepository();
        }
        return instance;
    }

    public void loadAllDataToCache() {
        for (Document document : rewardCollection.find()) {
            List<StreakRewardDto> streakRewardDtos = new ArrayList<>();
            Integer day = document.getInteger("day");
            List<String> rewards = document.getList("rewards", String.class);
            for (String reward : rewards) {
                String[] split = reward.split(":");
                String type = split[0];
                String id = split[1];
                Integer amount = Integer.parseInt(split[2]);
                StreakRewardDto streakRewardDto = StreakRewardDto.builder().id(id).type(type).amount(amount).build();
                streakRewardDtos.add(streakRewardDto);
            }
            rewardsCache.put(day, streakRewardDtos);
        }
    }
}
