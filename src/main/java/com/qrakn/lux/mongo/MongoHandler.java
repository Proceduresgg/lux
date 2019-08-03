package com.qrakn.lux.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

@Getter
public enum MongoHandler {

    INSTANCE;

    private MongoDatabase database;

    public void init() {
        MongoClientOptions options = MongoClientOptions
                .builder()
                .connectionsPerHost(50)
                .build();

        MongoCredential credential = MongoCredential.createCredential(
                "procedures",
                "lux",
                "lawde".toCharArray());

        MongoClient client = new MongoClient(new ServerAddress(
                "localhost",
                27017),
                credential,
                options);

        this.database = client.getDatabase("lux");
    }
}
