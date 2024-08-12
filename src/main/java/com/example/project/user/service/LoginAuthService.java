package com.example.project.user.service;

import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginAuthService {
    private final UserService userService;

    /**
     * Session에 등록될 객체
     */

    public UserResponse checkSession(HttpServletRequest request, String cookieValue) {
        HttpSession session = request.getSession(true);    // Session이 있으면 가져오고 없으면 null return
        Long attribute = (Long) session.getAttribute(
                Base64.getEncoder().encodeToString(cookieValue.getBytes())
        );
        if (attribute != null) {
            return userService.find(attribute);
        }
        return null;
    }

    @Async
    public void sessionRegistration(HttpServletRequest request, UserResponse response) {
        HttpSession session = request.getSession(true);
        session.setAttribute(Base64.getEncoder().encodeToString(response.getUserId().getBytes()), response.getId());
    }

    public Cookie cookieIssuance(LoginRequest request) {
        Cookie cookie = new Cookie(
                "DigitalLoginCookie",
                Base64.getEncoder().encodeToString(request.getUserId().getBytes())
        );

        cookie.setMaxAge(60 * 60);    //쿠키를 60초 동안 저장
        cookie.setSecure(true);
        cookie.setDomain("localhost"); //도메인을 localhost로 설정함
        cookie.setPath("/localhost:8080");  //쿠키의 설정 path를 locathost:8080으로 설정

        return cookie;
    }
}
