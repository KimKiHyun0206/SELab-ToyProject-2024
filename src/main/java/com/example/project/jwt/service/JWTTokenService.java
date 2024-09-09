package com.example.project.jwt.service;

import com.example.project.jwt.dto.TokenDto;
import com.example.project.jwt.dto.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class JWTTokenService {

    @Transactional
    public TokenResponse registerToken(){
        return null;
    }
}
