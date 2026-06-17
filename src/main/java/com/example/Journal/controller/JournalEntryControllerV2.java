package com.example.Journal.controller;

import com.example.Journal.Entry.Journal;
import com.example.Journal.Entry.User;
import com.example.Journal.service.JournalEntryService;
import com.example.Journal.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserEntryService userEntryService;

    @GetMapping()
    public ResponseEntity<List<Journal>> returnAllJournalsForUsername(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
          return new ResponseEntity<>(journalEntryService.getAllJournalsByUser(username),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping()
    public void createEntry(@RequestBody Journal newJournal) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            newJournal.setDate(LocalDateTime.now());
            journalEntryService.saveJournalEntry(newJournal, username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        }

   /* @GetMapping("id/{id}")
    public ResponseEntity<Journal> returnEntryById(@PathVariable ObjectId id){
        Optional<Journal> journal = journalEntryService.findJournalById(id);
        if(journal.isPresent()){
            return new ResponseEntity<>(journal.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

    @DeleteMapping("id/{id}")
    public void deleteEntryById(@PathVariable ObjectId id){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.deleteById(id,username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("id/{id}")
    public Journal putEntryById(@PathVariable ObjectId id , @RequestBody Journal newJournal){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            return journalEntryService.updateById(id, newJournal, username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

