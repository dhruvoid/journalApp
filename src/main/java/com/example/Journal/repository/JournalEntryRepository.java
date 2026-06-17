package com.example.Journal.repository;

import com.example.Journal.Entry.Journal;
import com.example.Journal.Entry.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JournalEntryRepository extends MongoRepository<Journal,String> {
    List<Journal> findByUser(User user);
    long deleteByUser(User user);
}
