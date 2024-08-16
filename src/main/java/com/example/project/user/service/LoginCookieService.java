package com.example.project.user.service;

import com.example.project.user.dto.login.LoginRequest;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class LoginCookieService {
    public Cookie cookieIssuance(LoginRequest request) {
        // 사용자 ID를 Base64로 인코딩하여 쿠키 값으로 사용
        String encodedUserId = Base64.getEncoder().encodeToString(request.getUserId().getBytes());

        Cookie cookie = new Cookie("DigitalLoginCookie", encodedUserId); // 인코딩된 사용자 ID 사용
        cookie.setMaxAge(60 * 60); // 쿠키를 1시간 동안 저장
        cookie.setSecure(true); // HTTPS에서만 전송되도록 설정
        cookie.setHttpOnly(true); // 클라이언트 측 스크립트에서 접근하지 못하도록 설정
        cookie.setPath("/"); // 애플리케이션 전체에서 쿠키 사용 가능

        return cookie;
    }
}
