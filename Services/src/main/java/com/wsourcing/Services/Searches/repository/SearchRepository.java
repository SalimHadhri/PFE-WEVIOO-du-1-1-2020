package com.wsourcing.Services.Searches.repository;


import com.wsourcing.Services.Accounts.model.Account;
import com.wsourcing.Services.Searches.model.Search;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends MongoRepository<Search,Integer> {

    Search findById(long id) ;

}
