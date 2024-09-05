package com.example.project.user.controller;

import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.service.CookieService;
import com.example.project.user.service.SessionService;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/users/register")
@RequiredArgsConstructor
public class UserRegisterController {
    private final UserService userService;
    private final SessionService sessionService;
    private final CookieService cookieService;

    @PostMapping
    public ResponseEntity<UserResponse> register(
            @RequestBody UserRegisterRequest userRegisterRequest,
            HttpServletResponse response,
            HttpServletRequest request
    ) throws IOException {
        log.info("User Register Request {}", userRegisterRequest.getUserId());

        UserResponse register = userService.register(userRegisterRequest);
        log.info("registered user {}", register.getId());
        Cookie cookie = cookieService.authCookieIssue(register.getUserId(), register.getPassword());
        log.info("cookie issue {}", cookie.getValue());

        sessionService.sessionRegistration(request, register);

        response.addCookie(cookie);

        return ResponseEntity.ok().body(new UserResponse(true, "회원가입이 완료되었습니다."));

    }
}
