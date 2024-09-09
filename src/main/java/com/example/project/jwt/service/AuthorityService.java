package com.example.project.jwt.service;

import com.example.project.jwt.dto.TokenDto;
import com.example.project.jwt.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Transactional
    public TokenDto registerToken(String token){
        return null;
    }

}
