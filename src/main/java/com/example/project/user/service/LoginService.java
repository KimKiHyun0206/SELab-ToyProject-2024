package com.example.project.user.service;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.user.InvalidLoginUserIdException;
import com.example.project.error.exception.user.InvalidLoginPasswordException;
import com.example.project.auth.token.TokenProvider;
import com.example.project.user.domain.User;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthTokenService authTokenService;

    @Transactional(readOnly = true)
    public UserResponse login(String id, String password) {
        User user = userRepository
                .findByUserId(id)
                .orElseThrow(InvalidLoginUserIdException::new);

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user.toResponseDto();
        } else throw new InvalidLoginPasswordException();
    }

    @Transactional
    public String userLogin(String id, String password) {
        User user = userRepository
                .findByUserId(id)
                .orElseThrow(InvalidLoginUserIdException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidLoginPasswordException();
        }

        String token = tokenProvider.createToken(user.getId(), user.getRoleType().getRole());

        authTokenService.registerUserToken(user.getId(), token);

        return token;
    }
}