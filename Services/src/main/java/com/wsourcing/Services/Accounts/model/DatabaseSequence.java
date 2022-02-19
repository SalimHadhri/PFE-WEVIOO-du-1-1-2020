package com.wsourcing.Services.Accounts.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//Used to auto-generate Account id with long type in the database
//The Auto-generate id is forbidden in MongoDB
//The id in MongoDB is a String type, type difficult to browse for complicated processing
@Document(collection = "database_sequences_accounts")
public class DatabaseSequence {

    @Id
    private String id;

    private long seq;

    public DatabaseSequence() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public DatabaseSequence(String id, long seq) {
        this.id = id;
        this.seq = seq;
    }

    public DatabaseSequence(long seq) {
        this.seq = seq;
    }
}

