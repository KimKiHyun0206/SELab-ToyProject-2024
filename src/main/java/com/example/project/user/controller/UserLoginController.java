package com.example.project.user.controller;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.auth.service.UserAuthService;
import com.example.project.auth.token.TokenProvider;
import com.example.project.auth.token.TokenResolver;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.service.CookieService;
import com.example.project.user.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserLoginController {
    private final LoginService loginService;
    private final AuthTokenService authTokenService;

    @PostMapping("/login")
    public void login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse httpServletResponse
    ) {
        log.info("jwtAuthLogin {}, {}", loginRequest.getUserId(), loginRequest.getPassword());

        try {

            String jwt = loginService.userLogin(loginRequest.getUserId(), loginRequest.getPassword());
            log.info("authrize jwt {}", jwt);

            httpServletResponse.setHeader("Authorization", jwt);
            httpServletResponse.setStatus(HttpStatus.OK.value());

        } catch (Exception e) {
            log.info("로그인 예외 발생 {}", e.getMessage());
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping("/login/token")
    public void tokenLogin(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        log.info("token login tryed {}", token);

        if(authTokenService.isValidateToken(token)){
            Long userIdByToken = authTokenService.getUserIdByToken(token);
            log.info("Login {}", userIdByToken);
            response.setStatus(HttpStatus.OK.value());
        }else response.setStatus(HttpStatus.BAD_REQUEST.value());
    }
}