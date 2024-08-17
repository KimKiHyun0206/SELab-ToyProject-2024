package com.example.project.user.service;

import com.example.project.user.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {
    private final UserService userService;
    private final EncodeService encodeService;

    public UserResponse getUser(HttpServletRequest request, String cookieValue) {
        HttpSession session = request.getSession(false); // 기존 세션 가져오기
        if (session == null) {
            log.info("No session found for the request.");
            return null;
        }

        // 쿠키 값도 Base64로 디코딩하여 세션 키와 비교
        log.info("Checking session with key: {}", cookieValue);  // 키 로그

        Long attribute = (Long) session.getAttribute(cookieValue); // 세션에서 속성 조회
        if (attribute != null) {
            log.info("Session found, user ID: {}", attribute);  // 세션에 저장된 사용자 ID 로그
            return userService.find(attribute);
        } else {
            log.info("No matching session found for key: {}", cookieValue);  // 세션이 없을 경우 로그
            return null;
        }
    }

    public void sessionRegistration(HttpServletRequest request, UserResponse response) {
        HttpSession session = request.getSession(true);

        // 사용자 ID를 Base64로 인코딩하여 세션 키로 사용
        String sessionKey = encodeService.userIdEncode(response.getUserId());

        session.setAttribute(sessionKey, response.getId()); // 인코딩된 사용자 ID를 키로 사용하여 ID를 세션에 저장
        log.info("Registering session with key: {}", sessionKey);
    }
    public void deleteSession(HttpServletRequest request, String cookieValue){
        HttpSession session = request.getSession(false); // 기존 세션 가져오기
        session.removeAttribute(cookieValue);
    }

}
