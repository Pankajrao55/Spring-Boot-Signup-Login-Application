package com.auth.service;

import com.auth.model.User;
import com.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user
     * Returns "success" or an error message
     */
    public String registerUser(User user) {
        // Check duplicate username
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Username already taken! Please choose another.";
        }

        // Check duplicate email
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email already registered! Please login.";
        }

        // Encrypt password using BCrypt before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save to DB
        userRepository.save(user);
        return "success";
    }

    /**
     * Validate login credentials
     * Returns true if username + password match
     */
    public boolean loginUser(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }
}
