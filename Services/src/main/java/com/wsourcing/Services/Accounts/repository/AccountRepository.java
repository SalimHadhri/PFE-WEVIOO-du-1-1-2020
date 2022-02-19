package com.wsourcing.Services.Accounts.repository;


import com.wsourcing.Services.Accounts.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//Repository referenced to our Account class
@Repository
public interface AccountRepository extends MongoRepository<Account,Integer> {

    Account findById(long id) ;


}
