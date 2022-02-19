package com.wsourcing.Services.Searches.service;



import com.wsourcing.Services.Searches.model.DatabaseSequenceSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.Objects;



//Used for the auto-generate method developed above
//Auto-generate long User id is forbidden in MongoDB
@Service
public class SequenceGeneratorServiceSearch {


    private MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorServiceSearch(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {

        Query query = new Query();
        DatabaseSequenceSearch counter = mongoOperations.findAndModify((query.addCriteria(Criteria.where("_id").is(seqName))),
                new Update().inc("seq",1), FindAndModifyOptions.options().returnNew(true).upsert(true),
                DatabaseSequenceSearch.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }


}
