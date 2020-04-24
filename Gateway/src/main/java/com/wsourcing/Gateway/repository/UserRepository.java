package com.wsourcing.Gateway.repository;


import com.wsourcing.Gateway.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,Integer> {

    User findById(int id) ;
    User findByUsername(String userName) ;
}
