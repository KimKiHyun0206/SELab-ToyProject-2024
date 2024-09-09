package com.example.project.home.controller;

import com.example.project.jwt.repository.AuthorityRepository;
import com.example.project.jwt.token.TokenResolver;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final TokenResolver tokenResolver;
    private final AuthorityRepository authorityRepository;

    @RequestMapping
    public String home(
            Model model,
            HttpServletRequest request
    ) {
        String token = tokenResolver.resolveToken(request);
        return null;
    }

    @RequestMapping(value = "/ranking")
    public String ranking(){
        return null;
    }

}
