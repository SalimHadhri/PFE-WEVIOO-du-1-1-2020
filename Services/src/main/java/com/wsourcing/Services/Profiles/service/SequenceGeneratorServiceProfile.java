package com.wsourcing.Services.Profiles.service;



import com.wsourcing.Services.Profiles.model.DatabaseSequenceProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SequenceGeneratorServiceProfile {


    private MongoOperations mongoOperations;
    private SequenceGeneratorServiceProfile sequenceGeneratorServiceProfile;


    @Autowired
    public SequenceGeneratorServiceProfile(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {

        Query query = new Query();
        DatabaseSequenceProfiles counter = mongoOperations.findAndModify((query.addCriteria(Criteria.where("_id").is(seqName))),
                new Update().inc("seq",1), FindAndModifyOptions.options().returnNew(true).upsert(true),
                DatabaseSequenceProfiles.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }

    public MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public SequenceGeneratorServiceProfile() {
    }
}
