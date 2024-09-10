package com.example.project.home.controller;

import com.example.project.user.repository.AuthorityRepository;
import com.example.project.auth.token.TokenResolver;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "non-authentication/main";
    }

    @RequestMapping(value = "/ranking")
    public String ranking(){
        return null;
    }

}
