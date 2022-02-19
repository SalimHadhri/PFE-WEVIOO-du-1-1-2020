package com.wsourcing.Services.Profiles.repository;

import com.wsourcing.Services.Profiles.model.DatabaseSequenceProfiles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


//Repository referenced to our DatabaseSequenceProfiles class
@Repository
public interface DatabaseSequenceProfilesRepository extends MongoRepository<DatabaseSequenceProfiles,String> {

    Optional<DatabaseSequenceProfiles> findById(String id) ;

}
