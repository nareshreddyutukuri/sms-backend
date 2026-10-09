package com.studentmanagement.dto;

import com.studentmanagement.model.Role;

public class AuthResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private String message;
    private String token;

    public AuthResponse() {
    }

    public AuthResponse(
            Long id,
            String username,
            String email,
            Role role,
            String message,
            String token) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.message = message;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}