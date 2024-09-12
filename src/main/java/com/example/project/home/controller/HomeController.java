package com.example.project.home.controller;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.user.dto.UserResponse;
import com.example.project.common.util.HeaderUtil;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final AuthTokenService authTokenService;
    private final UserService userService;
    private final HeaderUtil headerUtil;

    @RequestMapping
    public String home(
            @CookieValue(name = HeaderUtil.AUTHORIZATION_HEADER, required = false) Cookie cookie,
            Model model
    ) {
        if(cookie != null){
            String token = cookie.getValue();
            Long userIdByToken = authTokenService.getUserIdByToken(token);
            UserResponse userResponse = userService.find(userIdByToken);
            log.info("home token -> {}",token);
            model.addAttribute("user",userResponse.getName().getName());
            return "authentication/main";
        }

        return "non-authentication/main";
    }

    @RequestMapping(value = "/ranking")
    public String ranking(@CookieValue(name = HeaderUtil.AUTHORIZATION_HEADER, required = false) Cookie cookie) {
        if(cookie != null){
            String token = cookie.getValue();
            log.info("home token -> {}",token);
            return "authentication/ranking";
        }

        return "non-authentication/ranking";
    }

}
