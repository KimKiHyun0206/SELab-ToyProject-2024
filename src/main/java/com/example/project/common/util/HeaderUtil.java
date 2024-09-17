package com.example.project.common.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HeaderUtil {
    public static final String AUTHORIZATION_HEADER = "code-for-code-auth";
    /**
     * @implSpec 이 메소드에서는 request header에 포함되어있는 JWT 토큰 값을 가져오게 해준다
     * */
    public static String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        log.info("Resolve Token -> {}", token);
        return request.getHeader("code-for-code-auth");
    }
}