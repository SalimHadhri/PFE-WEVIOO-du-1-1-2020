package com.wsourcing.Gateway.repository;


import com.wsourcing.Gateway.model.DatabaseSequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatabaseSequenceRepository extends MongoRepository<DatabaseSequence,String> {

    Optional<DatabaseSequence> findById(String id) ;

}
