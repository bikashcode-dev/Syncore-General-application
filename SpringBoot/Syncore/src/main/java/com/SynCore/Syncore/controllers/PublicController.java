package com.SynCore.Syncore.controllers;

import com.SynCore.Syncore.entity.User;
import com.SynCore.Syncore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public ")
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveNewEntry(user);
    }

}
