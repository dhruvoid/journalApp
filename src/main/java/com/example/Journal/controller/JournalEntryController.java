package com.example.Journal.controller;

import com.example.Journal.Entry.Journal;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/journal")
public class JournalEntryController {

    private Map<ObjectId, Journal> journalEntries = new HashMap();

    @GetMapping
    public Map returnAllJournals(){
        return journalEntries;
    }

    @PostMapping
    public void createEntry(@RequestBody Journal newJournal) {
        journalEntries.put(newJournal.getId(),newJournal);
    }

    @GetMapping("id/{id}")
    public Journal returnEntryById(@PathVariable ObjectId id){
        return journalEntries.get(id);
    }

    @DeleteMapping("id/{id}")
    public Journal deleteEntryById(@PathVariable ObjectId id){
        return journalEntries.remove(id);
    }

    @PutMapping("id/{id}")
    public Journal putEntryById(@PathVariable ObjectId id , @RequestBody Journal newJournal){
        return journalEntries.put(id , newJournal);
    }
}

