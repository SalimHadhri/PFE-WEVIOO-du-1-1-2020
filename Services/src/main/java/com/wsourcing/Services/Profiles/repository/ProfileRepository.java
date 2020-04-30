package com.wsourcing.Services.Profiles.repository;

import com.wsourcing.Services.Profiles.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<Profile,Integer> {
    Profile findById(long id) ;
}
