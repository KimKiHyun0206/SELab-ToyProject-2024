package com.example.project.auth.service;

import com.example.project.auth.domain.AuthToken;
import com.example.project.auth.dto.AuthTokenResponse;
import com.example.project.auth.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthTokenService {
    private final AuthTokenRepository authTokenRepository;

    @Transactional(readOnly = true)
    public Long getUserIdByToken(String token){
        AuthToken authToken = authTokenRepository.findAuthTokenByToken(token);
        log.info("getUserIdByToken -> {}", authToken);
        return authToken.getUserId();
    }

    @Transactional
    public AuthTokenResponse registerToken(Long userId, String token){
        AuthToken saved = authTokenRepository.save(new AuthToken(userId,token));
        log.info("AuthToken Saved id:{}, userId:{}, token:{}",saved.getId(),saved.getUserId(),saved.getToken());

        return saved.toResponseDto();
    }

    @Transactional
    public boolean isValidateToken(String token){
        AuthToken foundAuthToken = authTokenRepository.findAuthTokenByToken(token);

        return foundAuthToken != null;
    }

    @Transactional
    public void deleteToken(String token){
        log.info("deleteToken -> {}", token);
        if (authTokenRepository.existsByToken(token)){
            log.info("token exists in database -> {}",token);
            authTokenRepository.deleteAuthTokenByToken(token);
            log.info("token deleted -> {}", token);
        }
    }
}
