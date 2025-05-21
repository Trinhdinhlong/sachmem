package com.example.sachmem.controller;


import com.example.sachmem.dto.reponse.JwtResponse;
import com.example.sachmem.dto.request.LoginRequest;
import com.example.sachmem.dto.request.RegisterRequest;
import com.example.sachmem.model.User;
import com.example.sachmem.service.AuthService;
import com.example.sachmem.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authServiceImpl;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        return authServiceImpl.login(request);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authServiceImpl.register(request);
        return "Đăng ký thành công!";
    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getProfile(
            @PathVariable("id") Integer userId
    ) {
        User user = authServiceImpl.getProfileById(userId);// Chuyển đổi sang DTO để ẩn thông tin nhạy cảm
        return ResponseEntity.ok(user);
    }
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers(){
        List<User> user = authServiceImpl.getAllStudents();// Chuyển đổi sang DTO để ẩn thông tin nhạy cảm
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Integer id) {
        authServiceImpl.deleteUser(id);
        return ResponseEntity.ok("User has been deactivated successfully.");
    }
    @PutMapping("/rollback/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> rollback(@PathVariable Integer id) {
        authServiceImpl.RollbackUser(id);
        return ResponseEntity.ok("User has been deactivated successfully.");
    }
}

