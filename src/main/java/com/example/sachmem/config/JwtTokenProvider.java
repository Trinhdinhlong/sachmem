package com.example.sachmem.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    // Sử dụng secret key cố định
    private final SecretKey secretKey;
    private final long EXPIRATION = 86400000; // 1 ngày

    public JwtTokenProvider() {
        // Khởi tạo secret key 1 lần duy nhất khi ứng dụng start
        this.secretKey = Keys.hmacShaKeyFor("your-256-bit-secret-key-must-be-at-least-32-chars".getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long id, String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .claim("id", id)
                .claim("roles", List.of(role)) // Sử dụng "roles" thay vì "role"
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey) // Sử dụng secret key cố định
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Thêm method quan trọng này
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}