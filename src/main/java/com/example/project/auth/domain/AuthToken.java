package com.example.project.auth.domain;

import com.example.project.auth.dto.AuthTokenResponse;
import com.example.project.common.BaseEntity;
import com.example.project.restrictions.Domain;
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
public class AuthToken extends BaseEntity implements Domain<AuthTokenResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String token;

    private String role;

    @Builder
    public AuthToken(Long userId, String token, String role) {
        this.userId = userId;
        this.token = token;
        this.role = role;
    }

    @Override
    public AuthTokenResponse toResponseDto() {
        return new AuthTokenResponse(
                this.id,
                this.userId,
                this.token,
                this.role
        );
    }
}
