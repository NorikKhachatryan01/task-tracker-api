package org.example.tasktrackerapi.controller;


import jakarta.persistence.Id;
import org.example.tasktrackerapi.model.User;
import org.example.tasktrackerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable long id) {
      return  userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }


    @GetMapping("/user/status")
    public List<String> statusCheck() {
        List<String> status = new ArrayList<>();
        for (int i = 1; i< 51; i++) {
            status.add("Status: " + i);
        }
        return status;
    }
}
