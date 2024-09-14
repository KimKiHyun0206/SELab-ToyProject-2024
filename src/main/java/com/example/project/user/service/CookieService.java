package com.example.project.user.service;

import com.example.project.common.util.HeaderUtil;
import com.example.project.user.dto.login.LoginRequest;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieService {

    private final EncodeService encodeService;

    public Cookie createJWTCookie(String token){
        Cookie cookie = new Cookie(HeaderUtil.AUTHORIZATION_HEADER, token);

        cookie.setDomain("localhost");
        cookie.setMaxAge(60 * 60); // 쿠키를 1시간 동안 저장
        cookie.setSecure(true); // HTTPS에서만 전송되도록 설정
        cookie.setHttpOnly(true); // 클라이언트 측 스크립트에서 접근하지 못하도록 설정
        cookie.setPath("/"); // 애플리케이션 전체에서 쿠키 사용 가능

        return cookie;
    }

    public Cookie authCookieIssue(String id, String password){
        String encodedUserId = encodeService.userIdEncode(id);

        return getCookie(encodedUserId);
    }

    private Cookie getCookie(String encodedUserId) {
        Cookie cookie = new Cookie(HeaderUtil.AUTHORIZATION_HEADER, encodedUserId); // 인코딩된 사용자 ID 사용
        cookie.setDomain("localhost");
        cookie.setMaxAge(60 * 60); // 쿠키를 1시간 동안 저장
        cookie.setSecure(true); // HTTPS에서만 전송되도록 설정
        cookie.setHttpOnly(true); // 클라이언트 측 스크립트에서 접근하지 못하도록 설정
        cookie.setPath("/"); // 애플리케이션 전체에서 쿠키 사용 가능

        return cookie;
    }

    public Cookie deleteCookie(){
        Cookie cookie = new Cookie(HeaderUtil.AUTHORIZATION_HEADER, null);
        cookie.setMaxAge(0);

        return cookie;
    }
}
