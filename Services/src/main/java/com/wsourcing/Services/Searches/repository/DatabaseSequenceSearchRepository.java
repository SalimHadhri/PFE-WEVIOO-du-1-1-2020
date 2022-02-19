package com.wsourcing.Services.Searches.repository;


import com.wsourcing.Services.Searches.model.DatabaseSequenceSearch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


//Repository referenced to our DatabaseSequenceSearch class
@Repository
public interface DatabaseSequenceSearchRepository extends MongoRepository<DatabaseSequenceSearch,String> {

    Optional<DatabaseSequenceSearch> findById(String id) ;

}
