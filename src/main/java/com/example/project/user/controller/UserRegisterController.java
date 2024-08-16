package com.example.project.user.controller;

import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.service.LoginSessionService;
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
public class UserRegisterController {
    private final UserService userService;
    private final LoginSessionService loginSessionService;

    @PostMapping
    public void register(
            @RequestBody UserRegisterRequest userRegisterRequest,
            HttpServletResponse response,
            HttpServletRequest request
    ) throws IOException {
        log.info("User Register Request {}", userRegisterRequest.getUserId());

        UserResponse register = userService.register(userRegisterRequest);
        loginSessionService.cookieIssuance(new LoginRequest(register.getUserId(), register.getPassword()));
        loginSessionService.sessionRegistration(request, register);

        response.sendRedirect("http://localhost:8080/");
    }
}
