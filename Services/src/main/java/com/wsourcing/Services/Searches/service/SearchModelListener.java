package com.wsourcing.Services.Searches.service;


import com.wsourcing.Services.Searches.model.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

//Used for the auto-generate method developed above
//Auto-generate long User id is forbidden in MongoDB
@Component
public class SearchModelListener extends AbstractMongoEventListener<Search> {

    private SequenceGeneratorServiceSearch sequenceGeneratorServiceSearch;

    @Autowired
    public SearchModelListener(SequenceGeneratorServiceSearch sequenceGeneratorServiceSearch) {
        this.sequenceGeneratorServiceSearch = sequenceGeneratorServiceSearch;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Search> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId((int) sequenceGeneratorServiceSearch.generateSequence((Search.getSequenceName())));
        }
    }
}
