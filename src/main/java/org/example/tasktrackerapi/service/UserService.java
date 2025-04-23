package org.example.tasktrackerapi.service;

import org.example.tasktrackerapi.model.User;
import org.example.tasktrackerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   public User createUser(@RequestBody User user) {
        return userRepository.save(user);
   }

   public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
   }

   public List<User> getAllUsers() {
        return userRepository.findAll();
   }
}
