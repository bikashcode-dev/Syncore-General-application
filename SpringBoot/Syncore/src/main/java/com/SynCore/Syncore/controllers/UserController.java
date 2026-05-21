package com.SynCore.Syncore.controllers;
import com.SynCore.Syncore.entity.User;
import com.SynCore.Syncore.repository.UserRepository;
import com.SynCore.Syncore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userEntryService;

    @PutMapping
    public ResponseEntity<?> updateUserEntry(@RequestBody User user )  {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userUpdate =  userEntryService.findByUsername(username);
        userUpdate.setUsername(user.getUsername());
        userUpdate.setPassword(user.getPassword());
        userEntryService.saveNewEntry(userUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
