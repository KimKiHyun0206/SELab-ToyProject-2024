package com.example.project.user.controller;

import com.example.project.auth.domain.UserDetail;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.SessionService;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        UserResponse userResponse = sessionService.checkSession(request, cookieValue);
        if (id.equals(userResponse.getId())) {
            UserResponse updateResponse = userService.updateUser(new UserDetail(userResponse.toEntity()), updateRequest);
        }
        response.sendRedirect("/localhost:8080/user/info");
    }
}
