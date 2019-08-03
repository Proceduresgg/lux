package com.qrakn.lux.match.ladder.handler;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.qrakn.lux.match.ladder.Ladder;
import com.qrakn.lux.mongo.MongoHandler;
import lombok.Getter;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum LadderHandler {

    INSTANCE;

    private final Map<String, Ladder> ladders = new HashMap<>();

    public void init() {
        MongoCollection<Document> collection = MongoHandler.INSTANCE.getDatabase().getCollection("ladders");

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                this.ladders.put(document.getString("name"), new Ladder(document.getString("name")).fromJSON(document));
            }
        }
    }

    public void save() {
        ladders.values().forEach(Ladder::save);
    }

    public Ladder createLadder(String name) {
        Ladder ladder = new Ladder(name);

        ladders.put(name, ladder);

        return ladder;
    }
}
