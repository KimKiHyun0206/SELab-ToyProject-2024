package com.example.project.main;

import com.example.project.user.dto.UserResponse;
import com.example.project.user.service.LoginAuthService;
import com.example.project.user.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final LoginService loginService;
    private final LoginAuthService loginAuthService;

    @RequestMapping
    public String main(
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (cookieValue == null) {
            return "non-authentication/main";
        } else{
            UserResponse userResponse = loginAuthService.checkSession(request, cookieValue);
            model.addAttribute("user",userResponse.getName().getName());
            return "authentication/main";
        }
    }
}
