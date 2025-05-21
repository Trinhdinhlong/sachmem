package com.example.sachmem.service;

import com.example.sachmem.dto.reponse.JwtResponse;
import com.example.sachmem.dto.request.LoginRequest;
import com.example.sachmem.dto.request.RegisterRequest;
import com.example.sachmem.model.User;
import com.example.sachmem.model.UserRole;

import java.util.List;

public interface AuthService {
    public JwtResponse login(LoginRequest request);
    public void register(RegisterRequest request);
    public List<User> getAllStudents();
    public User getProfileById(Integer userId);

    // ✅ Thêm mới:
    void deleteUser(Integer userId);
    void RollbackUser(Integer userId);
    User updateUser(Integer userId, RegisterRequest request);
}
