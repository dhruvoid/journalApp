package com.example.Journal.controller;

import com.example.Journal.Entry.User;
import com.example.Journal.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class HealthCheck {

    @Autowired
    UserEntryService userEntryService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok" ;
    }

    @PostMapping("/userEntry")
    public ResponseEntity<User> CreateUser(@RequestBody User newUser){
        try{
            userEntryService.CreateUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}

