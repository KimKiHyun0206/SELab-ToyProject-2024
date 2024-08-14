package com.example.project.auth.dto.request;

import lombok.Getter;

@Getter
public class JoinRequest {
    private String email;
    private String password;
}