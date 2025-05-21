package com.example.sachmem.repository;

import com.example.sachmem.model.User;
import com.example.sachmem.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Kiểm tra username đã tồn tại chưa
    boolean existsByUsername(String username);

    // Kiểm tra email đã tồn tại chưa
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByRole_RoleName(UserRole roleName);

}

