package com.wsourcing.Authentication.service;

import com.wsourcing.Authentication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

//Used for the auto-generate method developed above
//Auto-generate long User id is forbidden in MongoDB
@Component
public class UserModelListener extends AbstractMongoEventListener<User> {

    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public UserModelListener(com.wsourcing.Authentication.service.SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId((int) sequenceGeneratorService.generateSequence((User.getSequenceName())));
        }
    }
}
