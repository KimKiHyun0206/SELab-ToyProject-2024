package com.example.project.jwt.util;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationUser {
    private Long userId;
    private String username;
    private String password;

    public AuthenticationUser(String subject, String s, Collection<? extends GrantedAuthority> authorities) {

    }
}
