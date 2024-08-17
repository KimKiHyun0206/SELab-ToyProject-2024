package com.example.project.user.controller;

import com.example.project.auth.domain.UserDetail;
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
@RequestMapping("/api/user/info")
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
        UserResponse userResponse = sessionService.getUser(request, cookieValue);
        if (id.equals(userResponse.getId())) {
            UserResponse updateResponse = userService.updateUser(new UserDetail(userResponse.toEntity()), updateRequest);
        }
        response.sendRedirect("/localhost:8080/user/info");
    }

    //TODO 토큰 인증으로 마이페이지 접속할 수 있도록 추가하기
    @GetMapping("/my-page/{id}")
    public void myPage(Model model, @PathVariable Long id) {
        var userResponse = userService.find(id);
        log.info("[ SYSTEM ] MyPage user 조회 성공했습니다 {}", id);
        model.addAttribute("UserInfo", userResponse);
    }

    //TODO 토큰 인증으로 유저 정보 수정 가능하도록 만들기
    @GetMapping("/edit/{id}")
    public void editInfo(Model model, @PathVariable Long id) {
        var userResponse = userService.find(id);
        log.info("[ SYSTEM ] Edit user 조회 성공했습니다 {}", id);
        model.addAttribute("UserResponse", userResponse);
        model.addAttribute("UpdateRequest", new UserUpdateRequest());
    }
}
