package com.example.project.jwt.repository;

import com.example.project.jwt.domain.JWTToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JWTTokenRepository extends JpaRepository<JWTToken, Long> {
}
