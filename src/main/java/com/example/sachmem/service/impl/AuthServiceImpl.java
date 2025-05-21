package com.example.sachmem.service.impl;

import com.example.sachmem.config.JwtTokenProvider;
import com.example.sachmem.dto.reponse.JwtResponse;
import com.example.sachmem.dto.request.LoginRequest;
import com.example.sachmem.dto.request.RegisterRequest;
import com.example.sachmem.exception.DuplicateKeyException;
import com.example.sachmem.model.Role;
import com.example.sachmem.model.User;
import com.example.sachmem.model.UserRole;
import com.example.sachmem.repository.RoleRepository;
import com.example.sachmem.repository.UserRepository;
import com.example.sachmem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userRepository.findByUsername(request.getUsername()).get();
        String token = jwtTokenProvider.generateToken(user.getId() ,user.getUsername(), user.getRole().getRoleName().name());
        return new JwtResponse(token, user.getRole().getRoleName().name());
    }
    @Override
    public void register(RegisterRequest request) {
        // Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateKeyException("Username đã được sử dụng");
        }

        // Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateKeyException("Email đã được sử dụng");
        }

        // Tạo user mới
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setDateOfBirth(request.getDateOfBirth());

        // Set role mặc định là STUDENT
        Role studentRole = roleRepository.findByRoleName(UserRole.STUDENT)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role STUDENT"));
        user.setRole(studentRole);

        // Các giá trị mặc định
        user.setEnabled(true); // Hoặc false nếu cần xác thực email
        user.setCreatedAt(LocalDateTime.now());

        // Lưu user vào database
        userRepository.save(user);

        // Có thể thêm gửi email xác thực ở đây nếu cần
    }
    // Lấy tất cả user có role STUDENT
    @Override
    public List<User> getAllStudents() {
        return userRepository.findByRole_RoleName(UserRole.STUDENT);
    }

    // Lấy profile user theo ID
    @Override
    public User getProfileById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại với ID: " + userId));
    }
    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setEnabled(false); // hoặc setActive(false) tùy thuộc vào tên field
        userRepository.save(user);
    }
    @Override
    public void RollbackUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setEnabled(true); // hoặc setActive(false) tùy thuộc vào tên field
        userRepository.save(user);
    }

    @Override
    public User updateUser(Integer userId, RegisterRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Nếu có mã hóa
        return userRepository.save(user);
    }

}

