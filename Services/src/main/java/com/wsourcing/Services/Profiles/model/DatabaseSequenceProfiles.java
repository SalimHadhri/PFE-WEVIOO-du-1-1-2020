package com.wsourcing.Services.Profiles.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "database_sequences_profiles")
public class DatabaseSequenceProfiles {

    @Id
    private String id;

    private long seq;

    public DatabaseSequenceProfiles() {}

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

    public DatabaseSequenceProfiles(String id, long seq) {
        this.id = id;
        this.seq = seq;
    }

    public DatabaseSequenceProfiles(long seq) {
        this.seq = seq;
    }
}

