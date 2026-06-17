package com.example.Journal.controller;

import com.example.Journal.Entry.User;
import com.example.Journal.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    UserEntryService userEntryService;

    @GetMapping
    public ResponseEntity<List<User>> GetAllUsers(){
        try {
            return new ResponseEntity<>(userEntryService.GetAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/id/{userId}")
    public ResponseEntity<Optional<User>> GetUserById(@PathVariable ObjectId userId){
        try{
           Optional<User> user = userEntryService.GetUserById(userId);
            if(!user.isEmpty())
            return new ResponseEntity<>(user ,HttpStatus.OK);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Optional<User>> GetUserByUsername(@PathVariable String username){
        try{
            Optional<User> user = userEntryService.GetUserByUsername(username);
            if(!user.isEmpty())
                return new ResponseEntity<>(user ,HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping()
    public ResponseEntity<Optional<User>> DeleteUserByUsername(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<User> user = userEntryService.DeleteByUsername(username);
            if(!user.isEmpty())
                return new ResponseEntity<>(user,HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping()
    public ResponseEntity<Optional<User>> UpdateUserByUsernsme(@RequestBody User updatedUser){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
          Optional<User> user = userEntryService.UpdateById(username,updatedUser);
          if(!user.isEmpty()){
              return new ResponseEntity<>(user,HttpStatus.OK);
          }
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        }
}
