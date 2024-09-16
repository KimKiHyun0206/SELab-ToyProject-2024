package com.example.project.auth.repository;

import com.example.project.auth.domain.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    AuthToken findAuthTokenByToken(String token);
    boolean existsByToken(String token);
    void deleteAuthTokenByToken(String token);
}