package com.example.project.user.controller.ui;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.common.util.HeaderUtil;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthTokenService authTokenService;
    private final UserService userService;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("LoginRequest", new LoginRequest());
        return "/non-authentication/user/login";
    }

    @RequestMapping("/info")
    public String info(
            Model model,
            @CookieValue(value = HeaderUtil.AUTHORIZATION_HEADER, required = false) String cookie
    ) {
        log.info("info entry -> token: {}", cookie);
        if(cookie != null & authTokenService.isValidateToken(cookie)){
            log.info("token is validate");
            Long userId = authTokenService.getUserIdByToken(cookie);
            UserResponse userResponse = userService.find(userId);
            model.addAttribute("UserInfo", userResponse);
            return "/authentication/user/info";
        }
        return "/non-authentication/main";
    }

    @RequestMapping("/edit-info")
    public String editInfo(
            Model model,
            @CookieValue(value = HeaderUtil.AUTHORIZATION_HEADER, required = false) String cookie
    ) {
        if(cookie != null & authTokenService.isValidateToken(cookie)){
            Long userId = authTokenService.getUserIdByToken(cookie);
            UserResponse userResponse = userService.find(userId);
            model.addAttribute("UserInfo", userResponse);
            model.addAttribute("UpdateRequest", new UserUpdateRequest());
            return "/authentication/user/info";
        }

        return "/non-authentication/main";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("request", new UserRegisterRequest());
        return "/non-authentication/user/register";
    }
}