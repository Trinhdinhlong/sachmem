package com.example.sachmem.dto;

import com.example.sachmem.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserProfileDTO {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private LocalDateTime createdAt;
    // Constructor, getters, setters
}
