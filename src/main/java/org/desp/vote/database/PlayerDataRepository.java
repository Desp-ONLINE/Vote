package org.desp.vote.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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

    public boolean consumePlayerMonthlyCount(Player player, int price){
        int playerMonthlyVoteCount = getPlayerMonthlyVoteCount(player);
        if(playerMonthlyVoteCount < price){
            player.sendMessage("§c 월간 추천 포인트가 부족합니다! §7§o(현재 내 포인트: "+playerMonthlyVoteCount+")");
            return false;
        }
        consumePlayerVotePoint(player, price);
        return true;
    }


    public void insertDefaultPlayerData(Player player) {
        Document document = new Document()
                .append("user_id", player.getName())
                .append("uuid", player.getUniqueId().toString())
                .append("isVoted", false)
                .append("monthlyVoteCount", 0);

        playerList.insertOne(document);
    }

    public boolean isPlayerVoteToday(Player player) {
        Document document = playerList.find(Filters.eq("uuid", player.getUniqueId().toString())).first();
        return document.getBoolean("isVoted");
    }
    public int getPlayerMonthlyVoteCount(Player player) {
        Document document = playerList.find(Filters.eq("uuid", player.getUniqueId().toString())).first();
        return document.getInteger("monthlyVoteCount");
    }

    public boolean playerExists(Player player) {
        Document document = playerList.find(Filters.eq("uuid", player.getUniqueId().toString())).first();
        return document != null;
    }

    public void setPlayerVoteTrue(Player player) {
        Document filter = new Document()
                .append("uuid", player.getUniqueId().toString());

        Document first = playerList.find(filter).first();
        Integer monthlyVoteCount = first.getInteger("monthlyVoteCount")+1;

        Document update = new Document().append("isVoted", true).append("monthlyVoteCount", monthlyVoteCount);

        Document updateOperation = new Document("$set", update);

        playerList.updateOne(filter, updateOperation);
    }
    public void consumePlayerVotePoint(Player player, int amount) {
        Document filter = new Document()
                .append("uuid", player.getUniqueId().toString());

        int consumedPoint = getPlayerMonthlyVoteCount(player) - amount;

        Document update = new Document().append("monthlyVoteCount", consumedPoint);

        Document updateOperation = new Document("$set", update);

        playerList.updateOne(filter, updateOperation);
    }

    public void setPlayerVoteTrue(OfflinePlayer player) {
        Document filter = new Document()
                .append("uuid", player.getUniqueId().toString());

        Document first = playerList.find(filter).first();
        Integer monthlyVoteCount = first.getInteger("monthlyVoteCount")+1;

        Document update = new Document().append("isVoted", true).append("monthlyVoteCount", monthlyVoteCount);

        Document updateOperation = new Document("$set", update);

        playerList.updateOne(filter, updateOperation);
    }

    public void resetPlayerVote() {
        FindIterable<Document> documents = playerList.find();
        Document update = new Document().append("isVoted", false);
        Document updateOperation = new Document("$set", update);
        for (Document doc : documents) {
            playerList.updateOne(doc, updateOperation);
        }
    }
}
