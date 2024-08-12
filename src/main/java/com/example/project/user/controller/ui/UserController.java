package com.example.project.user.controller.ui;

import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.LoginAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final LoginAuthService loginAuthService;

    /**
     * <h1>LOGIN</h1>
     * */
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("LoginRequest", new LoginRequest());
        return "/non-authentication/user/login";
    }

    /**
     * <h1>INFO</h1>
     * */
    @RequestMapping("/info")
    public String info(
            Model model,
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue,
            HttpServletRequest request
    ) {
        if (cookieValue == null) return "/";

        UserResponse userResponse = loginAuthService.checkSession(request, cookieValue);
        model.addAttribute("UserInfo", userResponse);
        return "/authentication/user/info";
    }

    @RequestMapping("/edit-info")
    public String editInfo(
            Model model,
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue,
            HttpServletRequest request
    ) {
        if (cookieValue == null) return "/";

        UserResponse userResponse = loginAuthService.checkSession(request, cookieValue);
        model.addAttribute("UserInfo", userResponse);
        model.addAttribute("UpdateRequest", new UserUpdateRequest());

        return "/authentication/user/edit_info";
    }

    /**
     * <h1>REGISTER</h1>
     * */
    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("request", new UserRegisterRequest());
        return "/non-authentication/user/register";
    }
}
