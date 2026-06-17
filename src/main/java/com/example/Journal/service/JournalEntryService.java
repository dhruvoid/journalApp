package com.example.Journal.service;

import com.example.Journal.Entry.Journal;
import com.example.Journal.Entry.User;
import com.example.Journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    UserEntryService userEntryService;

    public void saveJournalEntry(Journal journal,String username){
        Optional<User> oUser = userEntryService.GetUserByUsername(username);
        if(!oUser.isEmpty()) {
            User user = oUser.get();
            journal.setUser(user);
        }
        else
            throw new RuntimeException("User Not Present");
        journal.setDate(LocalDateTime.now());
        journalEntryRepository.save(journal);
    }

    public List<Journal> getAllJournalsByUser(String username){
        Optional<User> oUser = userEntryService.GetUserByUsername(username);
        if(!oUser.isEmpty()){
            User user = oUser.get();
        return journalEntryRepository.findByUser(user);
        }
        throw new RuntimeException("User Not Present");
    }

    public Optional<Journal> findJournalById(ObjectId id){
        return journalEntryRepository.findById(String.valueOf(id));
    }

    public void deleteById(ObjectId id, String authenticatedUsername) throws Exception {
        Optional<User> oAuthenticatedUser = userEntryService.GetUserByUsername(authenticatedUsername);
        Optional<Journal> oJournal = journalEntryRepository.findById(String.valueOf(id));
        if(oJournal.isPresent()){
            Journal journal = oJournal.get();
            User journalOwner = journal.getUser();
            User authenticatedUser = oAuthenticatedUser.get();
            if(journalOwner.getUserId().equals(authenticatedUser.getUserId())){
                journalEntryRepository.deleteById(String.valueOf(id));
            }
            else{
                throw new Exception("The Authenticated user is not the owner of the journal");
            }
        }
        else{
            throw new Exception("journal not found with the given ID");
        }
    }

    public Journal updateById(ObjectId id, Journal newJournal,String authenticatedUsername) throws Exception {
        Journal old = journalEntryRepository.findById(String.valueOf(id)).orElse(null);
        if(old.getUser().getUserId().equals(userEntryService.GetUserByUsername(authenticatedUsername).get().getUserId())){
        if(old != null) {
            old.setTitle(newJournal.getTitle() != null && !newJournal.getTitle().equals("") ? newJournal.getTitle() : old.getTitle());
            old.setContext(newJournal.getContext() != null && !newJournal.getContext().equals("") ? newJournal.getContext() : old.getContext());
            old.setUpdateDate(LocalDateTime.now());
            journalEntryRepository.save(old);
        }
        }
        else{
            throw new Exception("authenticated user is not the owner of the journal");
        }
        return old;
    }
}
