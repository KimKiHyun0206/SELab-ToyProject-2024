package com.example.project.common.util;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.util.NotFoundClientIdHeaderException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class HttpUtil {

    /**
     * ip 검사
     *
     * @param request
     * @return
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        var headers = new String[]{
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        return Arrays.stream(headers)
                .map(request::getHeader)
                .filter(ip -> ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip))
                .findFirst()
                .orElse(request.getRemoteAddr());
    }

    /**
     * 쿠키 조회
     *
     * @param request 쿠키 조회
     * @return clientCookie UUID
     */
    public static String getClientIdFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new NotFoundClientIdHeaderException(ErrorMessage.NOT_FOUND_CLIENT_ID_HEADER);
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "client-id".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new NotFoundClientIdHeaderException(ErrorMessage.NOT_FOUND_CLIENT_ID_HEADER));
    }
}