package com.example.project.user.controller;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.auth.service.UserAuthService;
import com.example.project.common.util.HeaderUtil;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users/info")
@RequiredArgsConstructor
public class UserInfoApiController {
    private final UserService userService;
    private final AuthTokenService authTokenService;
    private final UserAuthService userAuthService;

    @PostMapping("/edit")
    public void editUser(
            @RequestBody UserUpdateRequest updateRequest,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        if (authTokenService.isValidateToken(HeaderUtil.resolveToken(httpServletRequest))) {
            var user = userAuthService.getUserByToken(HeaderUtil.resolveToken(httpServletRequest));
            if (user.getEmail().getEmail().equals(updateRequest.getEmail())) {
                var updateUser = userService.updateUser(updateRequest);
                log.info("editUser updateUser -> {}", updateUser.getId());
                httpServletResponse.setStatus(HttpStatus.OK.value());
            }
        } else httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
    }

    @GetMapping("/my-page")
    public void myPage(
            Model model,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        if (authTokenService.isValidateToken(HeaderUtil.resolveToken(httpServletRequest))) {
            var user = userAuthService.getUserByToken(HeaderUtil.resolveToken(httpServletRequest));
            log.info("userResponse id {}", user.getId());
            model.addAttribute("UserInfo", user);
            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
    }
}