package com.example.project.user.controller.ui;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.auth.service.UserAuthService;
import com.example.project.common.util.HeaderUtil;
import com.example.project.user.dto.request.UserUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/users/info")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserAuthService userAuthService;
    private final AuthTokenService authTokenService;

    @GetMapping("/edit")
    public void editInfo(
            Model model,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        if (authTokenService.isValidateToken(HeaderUtil.resolveToken(httpServletRequest))) {
            var user = userAuthService.getUserByToken(HeaderUtil.resolveToken(httpServletRequest));
            model.addAttribute("UserResponse", user);
            model.addAttribute("UpdateRequest", new UserUpdateRequest());
            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
    }
}