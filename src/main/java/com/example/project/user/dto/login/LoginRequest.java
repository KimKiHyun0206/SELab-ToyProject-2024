package com.example.project.user.dto.login;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    private String userId;
    private String password;
}
