package com.example.project.user.controller.ui;

import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.LoginSessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final LoginSessionService loginSessionService;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("LoginRequest", new LoginRequest());
        return "/non-authentication/user/login";
    }

    @RequestMapping("/info")
    public String info(
            Model model,
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue,
            HttpServletRequest request
    ) {
        if (cookieValue == null) return "";

        UserResponse userResponse = loginSessionService.checkSession(request, cookieValue);
        model.addAttribute("UserInfo", userResponse);
        return "/authentication/user/info";
    }

    @RequestMapping("/edit-info")
    public String editInfo(
            Model model,
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue,
            HttpServletRequest request
    ) {
        if (cookieValue == null) return "";

        UserResponse userResponse = loginSessionService.checkSession(request, cookieValue);
        model.addAttribute("UserInfo", userResponse);
        model.addAttribute("UpdateRequest", new UserUpdateRequest());

        return "/authentication/user/edit_info";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("request", new UserRegisterRequest());
        return "/non-authentication/user/register";
    }

    @GetMapping("/logout")
    public String logout(
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (cookieValue != null) {
            loginSessionService.deleteSession(request, cookieValue);
        }
        return "/non-authentication/main";
    }
}