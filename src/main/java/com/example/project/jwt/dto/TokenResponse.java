package com.example.project.jwt.dto;

import lombok.Data;

@Data
public class TokenResponse {
    private Long id;

    private Long userId;

    private String tokenValue;
}
