package com.wsourcing.Services.Accounts.repository;


import com.wsourcing.Services.Accounts.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<Account,Integer> {

    Account findById(long id) ;


}
