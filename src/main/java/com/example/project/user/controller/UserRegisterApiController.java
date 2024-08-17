package com.example.project.user.controller;

import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.service.CookieService;
import com.example.project.user.service.SessionService;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/user/register")
@RequiredArgsConstructor
public class UserRegisterApiController {
    private final UserService userService;
    private final SessionService sessionService;
    private final CookieService cookieService;

    @PostMapping
    public void register(
            @RequestBody UserRegisterRequest userRegisterRequest,
            HttpServletResponse response,
            HttpServletRequest request
    ) throws IOException {
        log.info("User Register Request {}", userRegisterRequest.getUserId());

        UserResponse register = userService.register(userRegisterRequest);
        cookieService.authCookieIssue(new LoginRequest(register.getUserId(), register.getPassword()));
        sessionService.sessionRegistration(request, register);

        response.sendRedirect("http://localhost:8080/");
    }
}
