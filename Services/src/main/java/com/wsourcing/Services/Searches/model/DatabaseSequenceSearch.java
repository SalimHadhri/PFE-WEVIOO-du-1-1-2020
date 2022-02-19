package com.wsourcing.Services.Searches.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


//Used to auto-generate Search id with long type in the database
//The Auto-generate id is forbidden in MongoDB
//The id in MongoDB is a String type, type difficult to browse for complicated processing */
@Document(collection = "database_sequences_searches")
public class DatabaseSequenceSearch {

    @Id
    private String id;
    private long seq;

    public DatabaseSequenceSearch() {}

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

    public DatabaseSequenceSearch(String id, long seq) {
        this.id = id;
        this.seq = seq;
    }

    public DatabaseSequenceSearch(long seq) {
        this.seq = seq;
    }

}
