package com.example.Journal.repository;

import com.example.Journal.Entry.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<User,String > {
    User findByUsername(String username);
    Void deleteByUsername(String username);
}
