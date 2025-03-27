package org.desp.vote.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bukkit.Bukkit;
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

    public void insertDefaultPlayerData(Player player) {
        Document document = new Document()
                .append("user_id", player.getName())
                .append("uuid", player.getUniqueId().toString())
                .append("isVoted", false);

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
