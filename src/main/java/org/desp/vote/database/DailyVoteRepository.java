package org.desp.vote.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class DailyVoteRepository {

    private static DailyVoteRepository instance;
    private final MongoCollection<Document> dailyVoteCollection;

    public DailyVoteRepository() {
        DatabaseRegister database = new DatabaseRegister();
        this.dailyVoteCollection = database.getDatabase().getCollection("DailyVote");
    }

    public static DailyVoteRepository getInstance() {
        if (instance == null) {
            instance = new DailyVoteRepository();
        }
        return instance;
    }


    public Integer getDailyVote(){
        Document document = dailyVoteCollection.find().first();
        Integer dailyVote = document.getInteger("DailyVote");
        return dailyVote;
    }
    public void addDailyVote(){
        int newDailyVote = getDailyVote() + 1;
        Document document = dailyVoteCollection.find().first();
        dailyVoteCollection.replaceOne(document, new Document("DailyVote", newDailyVote));
    }
    public void resetDailyVote(){
        Document document = dailyVoteCollection.find().first();
        dailyVoteCollection.replaceOne(document, new Document("DailyVote", 0));
    }


}
