package com.example.animerating.services;

import com.example.animerating.models.User;
import com.example.animerating.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByUsername(name);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
