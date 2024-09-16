package com.example.project.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthTokenResponse {
    private Long id;
    private Long userId;
    private String tokenValue;
    private String role;
}