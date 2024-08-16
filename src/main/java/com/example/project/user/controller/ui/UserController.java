package com.example.project.user.controller.ui;

import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.LoginAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
        if (cookieValue == null) return "";

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
        if (cookieValue == null) return "";

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

    /**
     * <h1>LOGOUT</h1>
     * 세션을 삭제하고 로그아웃 처리
     */
    @GetMapping("/logout")
    public String logout(
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue,
            HttpServletRequest request
    ) {
        if (cookieValue != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(cookieValue); // 세션에서 해당 속성 제거
                session.invalidate(); // 세션 무효화 (전체 세션 종료)
            }
        }

        // 로그아웃 후 로그인 페이지로 리다이렉트
        return "/non-authentication/main";
    }
}
