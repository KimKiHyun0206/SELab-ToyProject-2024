package com.example.project.user.controller.ui;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.auth.service.UserAuthService;
import com.example.project.common.util.HeaderUtil;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/users/info")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserAuthService userAuthService;
    private final AuthTokenService authTokenService;

    @RequestMapping
    public String info(
            Model model,
            HttpServletRequest httpServletRequest
    ) {
        String token = HeaderUtil.resolveToken(httpServletRequest);
        log.info("info entry -> token: {}", token);
        if (authTokenService.isValidateToken(token)) {
            log.info("token is validate");
            UserResponse userResponse = userAuthService.getUserByToken(token);
            model.addAttribute("UserInfo", userResponse);
            return "/auth/user/info";
        }
        return "/non-auth/main";
    }

    @RequestMapping("/edit")
    public String editInfo(
            Model model,
            HttpServletRequest httpServletRequest
    ) {
        String token = HeaderUtil.resolveToken(httpServletRequest);

        if (authTokenService.isValidateToken(token)) {
            UserResponse userResponse = userAuthService.getUserByToken(token);
            model.addAttribute("UserInfo", userResponse);
            model.addAttribute("UpdateRequest", new UserUpdateRequest());
            return "/auth/user/info";
        }

        return "/non-auth/main";
    }
}