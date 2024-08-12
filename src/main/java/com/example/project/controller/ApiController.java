package com.example.project.controller;

import ch.qos.logback.core.model.Model;
import com.example.project.dto.LoginForm;
import com.example.project.dto.SignupForm;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/login")
    public void login(LoginForm loginForm, HttpServletResponse response) throws IOException {
        log.info("[ SYSTEM ] Login Tried ID : {}", loginForm.getUserId());
        log.info("[ SYSTEM ] Login Tried PASSWORD : {}", loginForm.getPassword());

        response.sendRedirect("http://localhost:8080");
    }

    @PostMapping("/signup")
    public void signUp(SignupForm signupForm, HttpServletResponse response) throws IOException {
        log.info("[ SYSTEM ] REGISTER Tried ID : {}", signupForm.getUserId());
        log.info("[ SYSTEM ] REGISTER Tried PASSWORD : {}", signupForm.getPassword());

        response.sendRedirect("http://localhost:8080");
    }
}
