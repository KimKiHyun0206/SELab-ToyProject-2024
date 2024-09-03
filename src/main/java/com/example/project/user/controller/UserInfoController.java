package com.example.project.user.controller;

import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.SessionService;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/users/info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;
    private final SessionService sessionService;

    @PostMapping("/edit/{id}")
    public void editUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest updateRequest,
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        log.info("editUser {}", id);
        var userResponse = sessionService.getUser(request, cookieValue);
        log.info("userResponse id {}", userResponse.getId());
        if (id.equals(userResponse.getId())) {
            userService.updateUser(updateRequest);
        }
        response.sendRedirect("/localhost:8080/user/info");
    }

    //TODO 토큰 인증으로 마이페이지 접속할 수 있도록 추가하기
    @GetMapping("/my-page/{id}")
    public void myPage(
            Model model,
            @PathVariable Long id,
            HttpServletResponse response

    ) throws IOException {
        log.info("myPage {}", id);
        var userResponse = userService.find(id);
        log.info("userResponse id {}", userResponse.getId());
        model.addAttribute("UserInfo", userResponse);
        response.sendRedirect("/localhost:8080/user/info");
    }

    //TODO 토큰 인증으로 유저 정보 수정 가능하도록 만들기
    @GetMapping("/edit/{id}")
    public void editInfo(
            Model model,
            @PathVariable Long id,
            HttpServletResponse response
    ) throws IOException {
        log.info("editInfo {}", id);
        var userResponse = userService.find(id);
        log.info("userResponse id {}", userResponse.getId());
        model.addAttribute("UserResponse", userResponse);
        model.addAttribute("UpdateRequest", new UserUpdateRequest());
        response.sendRedirect("/localhost:8080/user/info");
    }
}
