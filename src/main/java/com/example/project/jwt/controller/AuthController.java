package com.example.project.jwt.controller;

import com.example.project.jwt.service.AuthorityService;
import com.example.project.jwt.token.JwtFilter;
import com.example.project.jwt.token.TokenProvider;
import com.example.project.jwt.dto.TokenDto;
import com.example.project.jwt.token.TokenResolver;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.service.LoginService;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final TokenResolver tokenResolver;
    private final UserService userService;

    private final LoginService loginService;


    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginRequest loginDto) {
        String token = loginService.userLogin(loginDto.getUserId(), loginDto.getPassword());

        log.info("authrize jwt {}", token);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(TokenResolver.AUTHORIZATION_HEADER, "Bearer " + token);

        return new ResponseEntity<>(new TokenDto(token), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<TokenDto> getToken(HttpServletRequest request){
        String s = tokenResolver.resolveToken(request);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(TokenResolver.AUTHORIZATION_HEADER, "Bearer " + s);

        return new ResponseEntity<>(new TokenDto(s), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(
            @Valid @RequestBody UserRegisterRequest userDto
    ) {
        return ResponseEntity.ok(userService.register(userDto));
    }
}