package com.example.Journal.service;

import com.example.Journal.Entry.User;
import com.example.Journal.repository.JournalEntryRepository;
import com.example.Journal.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserEntryService {

    @Autowired
    UserEntryRepository userEntryRepository;

    @Autowired
    JournalEntryRepository journalEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> GetAllUsers(){
        return userEntryRepository.findAll();
    }

    public void CreateUser(User newUser){
        newUser.setDate(LocalDateTime.now());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(Arrays.asList("USER"));
         userEntryRepository.save(newUser);
    }

    public Optional<User> GetUserById(ObjectId userId){
        return userEntryRepository.findById(String.valueOf(userId));
    }

    public Optional<User> GetUserByUsername(String username){
        return Optional.ofNullable(userEntryRepository.findByUsername(username));
    }

    @Transactional
    public Optional<User> DeleteByUsername(String username){
        Optional<User> ouser= GetUserByUsername(username);
        if(!ouser.isEmpty()) {
            User user = ouser.get();
            journalEntryRepository.deleteByUser(user);
            userEntryRepository.deleteByUsername(username);
        }
        return ouser;
    }

    public Optional<User> UpdateById(String username, User updatedUser){
        Optional<User> optionalUser = GetUserByUsername(username);
        if(optionalUser.isEmpty()){
            return Optional.empty();
        }
        User user = optionalUser.get();
        user.setUsername(updatedUser.getUsername()!=null && updatedUser.getUsername()!=("")?updatedUser.getUsername():user.getUsername());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()!=null && updatedUser.getPassword()!=("")? updatedUser.getPassword() : user.getPassword()));
        user.setUpdateDate(LocalDateTime.now());
        userEntryRepository.save(user);
        return Optional.of(user);
    }
}
