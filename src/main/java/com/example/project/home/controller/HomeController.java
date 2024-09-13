package com.example.project.home.controller;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.common.util.HeaderUtil;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.service.UserService;
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

    @RequestMapping
    public String home(
            @CookieValue(name = HeaderUtil.AUTHORIZATION_HEADER, required = false) String token,
            Model model
    ) {
        if(token != null && authTokenService.isValidateToken(token)){
            Long userIdByToken = authTokenService.getUserIdByToken(token);
            UserResponse userResponse = userService.find(userIdByToken);
            log.info("home token -> {}",token);
            model.addAttribute("user",userResponse.getName().getName());
            return "authentication/main";
        }

        return "non-authentication/main";
    }

    @RequestMapping(value = "/ranking")
    public String ranking(@CookieValue(name = HeaderUtil.AUTHORIZATION_HEADER, required = false) String token) {
        if(token != null){
            log.info("home token -> {}",token);
            return "authentication/ranking";
        }

        return "non-authentication/ranking";
    }

}
