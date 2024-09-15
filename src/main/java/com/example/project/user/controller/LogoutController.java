package com.example.project.user.controller;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.common.util.HeaderUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/users/logout")
@RequiredArgsConstructor
public class LogoutController {
    private final AuthTokenService authTokenService;

    @PostMapping
    public void logout(
            @CookieValue(value = HeaderUtil.AUTHORIZATION_HEADER)Cookie cookie,
            HttpServletResponse httpServletResponse
    ) throws IOException {
        log.info("logout by token -> {}", cookie.getValue());
        authTokenService.deleteToken(cookie.getValue());
        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
    }
}