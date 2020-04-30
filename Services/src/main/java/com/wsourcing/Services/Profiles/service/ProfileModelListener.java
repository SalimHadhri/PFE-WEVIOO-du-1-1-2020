package com.wsourcing.Services.Profiles.service;



import com.wsourcing.Services.Profiles.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ProfileModelListener extends AbstractMongoEventListener<Profile> {

    private SequenceGeneratorServiceProfile sequenceGeneratorServiceProfile;

    @Autowired
    public ProfileModelListener(SequenceGeneratorServiceProfile sequenceGeneratorServiceProfile) {
        this.sequenceGeneratorServiceProfile = sequenceGeneratorServiceProfile;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Profile> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId((int) sequenceGeneratorServiceProfile.generateSequence((Profile.getSequenceName())));
        }
    }
}
