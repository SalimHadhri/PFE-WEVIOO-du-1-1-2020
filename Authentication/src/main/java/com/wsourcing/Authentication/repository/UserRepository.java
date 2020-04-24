package com.wsourcing.Authentication.repository;


import com.wsourcing.Authentication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,Integer> {

    User findById(int id) ;
    User findByUsername(String userName) ;
}
