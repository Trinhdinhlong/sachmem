package com.example.sachmem.dto.reponse;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String role;

    public JwtResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
    // Getters và Setters
}

