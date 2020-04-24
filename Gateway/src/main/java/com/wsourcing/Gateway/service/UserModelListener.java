package com.wsourcing.Gateway.service;

import com.wsourcing.Gateway.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class UserModelListener extends AbstractMongoEventListener<User> {

    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public UserModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId((int) sequenceGeneratorService.generateSequence((User.getSequenceName())));
        }
    }
}
