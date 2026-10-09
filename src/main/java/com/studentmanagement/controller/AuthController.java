package com.studentmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagement.dto.AuthResponse;
import com.studentmanagement.dto.LoginRequest;
import com.studentmanagement.dto.RegisterRequest;
import com.studentmanagement.model.Role;
import com.studentmanagement.model.User;
import com.studentmanagement.service.AuthService;
import com.studentmanagement.service.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174"
})
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(
            AuthService authService,
            JwtService jwtService) {

        this.authService = authService;
        this.jwtService = jwtService;
    }

    // REGISTER STUDENT
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        // Public registration always creates a STUDENT account.
        User user = authService.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                Role.STUDENT
        );

        AuthResponse response = new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                "Registration successful",
                null
        );

        return ResponseEntity.ok(response);
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        User user = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        AuthResponse response = new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                "Login successful",
                token
        );

        return ResponseEntity.ok(response);
    }
    
}
