package com.SynCore.Syncore.controllers;

import com.SynCore.Syncore.entity.User;
import com.SynCore.Syncore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAllUserEntries() {
         List <User> allUserEntries = userService.getAllUserEntries();
          if (allUserEntries != null&& ! allUserEntries.isEmpty()){
             return new ResponseEntity<>(allUserEntries, HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
