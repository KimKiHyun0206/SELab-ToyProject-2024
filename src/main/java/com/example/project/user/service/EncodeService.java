package com.example.project.user.service;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EncodeService {
    public String userIdEncode(String userId) {
        return Base64
                .getEncoder()
                .encodeToString(userId.getBytes());
    }

    public String userIdAndPasswordEncode(String userId, String password) {
        return Base64
                .getEncoder()
                .encodeToString((userId + "," + password).getBytes());
    }

    public String decode(String str) {
        return new String(Base64.getDecoder().decode(str));
    }
}
