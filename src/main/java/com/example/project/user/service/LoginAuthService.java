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
        HttpSession session = request.getSession(false); // 기존 세션 가져오기
        if (session == null) {
            log.info("No session found for the request.");
            return null;
        }

        // 쿠키 값도 Base64로 디코딩하여 세션 키와 비교
        String sessionKey = cookieValue;  // 쿠키 값 사용
        log.info("Checking session with key: {}", sessionKey);  // 키 로그

        Long attribute = (Long) session.getAttribute(sessionKey); // 세션에서 속성 조회
        if (attribute != null) {
            log.info("Session found, user ID: {}", attribute);  // 세션에 저장된 사용자 ID 로그
            return userService.find(attribute);
        } else {
            log.info("No matching session found for key: {}", sessionKey);  // 세션이 없을 경우 로그
            return null;
        }
    }


    public void sessionRegistration(HttpServletRequest request, UserResponse response) {
        HttpSession session = request.getSession(true);

        // 사용자 ID를 Base64로 인코딩하여 세션 키로 사용
        String sessionKey = Base64.getEncoder().encodeToString(response.getUserId().getBytes());
        session.setAttribute(sessionKey, response.getId()); // 인코딩된 사용자 ID를 키로 사용하여 ID를 세션에 저장
        log.info("Registering session with key: {}", sessionKey);
    }




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
