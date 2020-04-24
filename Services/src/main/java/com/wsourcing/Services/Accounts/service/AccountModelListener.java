package com.wsourcing.Services.Accounts.service;


import com.wsourcing.Services.Accounts.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class AccountModelListener extends AbstractMongoEventListener<Account> {

    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public AccountModelListener(com.wsourcing.Services.Accounts.service.SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Account> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId((int) sequenceGeneratorService.generateSequence((Account.getSequenceName())));
        }
    }
}
