package com.wsourcing.Authentication.repository;

import com.wsourcing.Authentication.model.DatabaseSequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

//Repository referenced to our DatabaseSequence class
@Repository
public interface DatabaseSequenceRepository extends MongoRepository<DatabaseSequence,String> {

    Optional<DatabaseSequence> findById(String id) ;

}
