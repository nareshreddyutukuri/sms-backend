package com.studentmanagement.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.studentmanagement.model.Role;
import com.studentmanagement.model.User;
import com.studentmanagement.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTER
    public User register(
            String username,
            String email,
            String password,
            Role role) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException(
                    "Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException(
                    "Email already exists");
        }

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);

        // Encrypt password before saving
        user.setPassword(
                passwordEncoder.encode(password)
        );

        user.setRole(role);

        return userRepository.save(user);
    }

    // LOGIN
    public User login(String username, String password) {

    System.out.println("LOGIN STEP 1: Searching user");

    User user = userRepository
            .findByUsername(username)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED,
                            "Invalid username or password"));

    System.out.println("LOGIN STEP 2: User found");

    if (!passwordEncoder.matches(
            password, user.getPassword())) {

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid username or password");
    }

    System.out.println("LOGIN STEP 3: Password verified");

    return user;
}

public void resetPassword(String username, String newPassword) {
    if (newPassword == null || newPassword.length() < 8) {
        throw new IllegalArgumentException(
                "Password must contain at least 8 characters");
    }

    User user = userRepository.findByUsername(username)
            .orElseThrow(() ->
                    new RuntimeException("User not found: " + username));

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
}
}