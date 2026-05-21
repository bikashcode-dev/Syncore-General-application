package com.SynCore.Syncore.service;


import com.SynCore.Syncore.entity.User;
import com.SynCore.Syncore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public void saveNewEntry(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepository.save(user);
    }
    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllUserEntries() {
        return userRepository.findAll();
    }

    public  Optional<User> getUserEntryById(Object id) {
        return userRepository.findById(id);
    }

    public void deleteEntryByUserId(Object id) {
        userRepository.deleteById(id);
    }

    public User updateEntryByUserId(Object id, User user) {

        if(userRepository.findById(id).isPresent()) {
            userRepository.save(user);
        }
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
