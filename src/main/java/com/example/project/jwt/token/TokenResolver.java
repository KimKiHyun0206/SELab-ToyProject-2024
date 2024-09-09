package com.example.project.jwt.token;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TokenResolver {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    /**
     * @implSpec 이 메소드에서는 request header에 포함되어있는 JWT 토큰 값을 가져오게 해준다
     * */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

}
