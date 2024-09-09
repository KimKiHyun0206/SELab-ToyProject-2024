package com.example.project.jwt.domain;

import com.example.project.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class JWTToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String tokenValue;

    @Builder
    public JWTToken(Long userId, String tokenValue) {
        this.userId = userId;
        this.tokenValue = tokenValue;
    }
}
