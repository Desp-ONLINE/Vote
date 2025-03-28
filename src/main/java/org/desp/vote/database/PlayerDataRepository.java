package org.desp.vote.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.*;

public class PlayerDataRepository {

    private static PlayerDataRepository instance;
    private final MongoCollection<Document> playerList;


    public PlayerDataRepository() {
        DatabaseRegister database = new DatabaseRegister();
        this.playerList = database.getDatabase().getCollection("PlayerData");
    }

    public static PlayerDataRepository getInstance() {
        if (instance == null) {
            instance = new PlayerDataRepository();
        }
        return instance;
    }

    public void dropAllPlayerData(){
        playerList.deleteMany(new Document());
    }

    public int getPlayerVoteStreak(Player player){
        Document first = playerList.find(new Document("uuid", player.getUniqueId().toString())).first();

        return first.getInteger("voteStreak");
    }
    public List<Integer> getPlayerMonthlyVotedDate(Player player){
        Document first = playerList.find(new Document("uuid", player.getUniqueId().toString())).first();

        return first.getList("monthlyVotedDate", Integer.class);
    }

    public void insertDefaultPlayerData(Player player) {
        List<Integer> list = new ArrayList<Integer>();
        Document document = new Document()
                .append("user_id", player.getName())
                .append("uuid", player.getUniqueId().toString())
                .append("isVoted", false)
                .append("voteStreak", 0)
                .append("monthlyVotedDate", list);

        playerList.insertOne(document);
    }

    public boolean isPlayerVoteToday(Player player) {
        Document document = playerList.find(Filters.eq("uuid", player.getUniqueId().toString())).first();
        return document.getBoolean("isVoted");
    }

    public boolean playerExists(Player player) {
        Document document = playerList.find(Filters.eq("uuid", player.getUniqueId().toString())).first();
        return document != null;
    }

    public void setPlayerVoteTrue(Player player) {
        Document filter = new Document()
                .append("uuid", player.getUniqueId().toString());

        Document first = playerList.find(filter).first();
        Integer voteStreak = first.getInteger("voteStreak");
        List<Integer> monthlyVotedDate = first.getList("monthlyVotedDate", Integer.class);


        int dayOfMonth = LocalDateTime.now().getDayOfMonth();
        if(monthlyVotedDate.contains(dayOfMonth-1) || voteStreak == 0){
            voteStreak+=1;
        }
        monthlyVotedDate.add(dayOfMonth);
        Set<Integer> set = new HashSet<>(monthlyVotedDate);
        List<Integer> result = new ArrayList<>(set);

        Document update = new Document().append("isVoted", true).append("voteStreak", voteStreak).append("monthlyVotedDate", result);

        Document updateOperation = new Document("$set", update);

        playerList.updateOne(filter, updateOperation);
    }

    public void setPlayerVoteTrue(OfflinePlayer player) {
        Document document = new Document()
                .append("user_id", player.getName())
                .append("uuid", player.getUniqueId().toString())
                .append("isVoted", true);

        playerList.replaceOne(
                Filters.eq("uuid", player.getUniqueId().toString()),
                document,
                new ReplaceOptions().upsert(true)
        );
    }

    public void resetPlayerVote() {
        try {
            playerList.deleteMany(new Document());
            System.out.println("추천 플레이어 디비 삭제 완료");
        } catch (Exception e) {
            System.out.println("추천 플레이어 디비 삭제 중 오류 발생");
        }
    }
}
