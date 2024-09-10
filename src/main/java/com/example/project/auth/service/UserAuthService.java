package com.example.project.auth.service;

import com.example.project.user.dto.UserResponse;
import com.example.project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserService userService;
    private final AuthTokenService authTokenService;

    public UserResponse getUserByToken(String token){
        if(authTokenService.isValidateToken(token)){
            return userService.find(authTokenService.getUserIdByToken(token));
        }
        log.info("UserAuthService.getUserByToken({}) -> 토큰이 유효하지 않아 User를 찾을 수 없습니다",token);
        return null;
    }
}
