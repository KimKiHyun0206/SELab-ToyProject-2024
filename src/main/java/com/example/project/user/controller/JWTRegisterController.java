package com.example.project.user.controller;

import com.example.project.jwt.dto.TokenDto;
import com.example.project.jwt.token.TokenProvider;
import com.example.project.jwt.token.TokenResolver;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users/jwt")
@RequiredArgsConstructor
public class JWTRegisterController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(
            @Valid @RequestBody UserRegisterRequest userDto
    ) {
        UserResponse register = userService.register(userDto);

        String jwt = tokenProvider.createToken(register.getId(),register.getRoleType().getRole());
        log.info("authrize jwt {}", jwt);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(TokenResolver.AUTHORIZATION_HEADER, "Bearer " + jwt);


        return new ResponseEntity<>(register, httpHeaders, HttpStatus.OK);
    }
}
