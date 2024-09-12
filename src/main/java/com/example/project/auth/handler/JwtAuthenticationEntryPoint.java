package com.example.project.auth.handler;

import com.example.project.common.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
   @Override
   public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException {
      // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
      String token = request.getHeader(HeaderUtil.AUTHORIZATION_HEADER);
      log.info("유효한 자격증명이 없는 접근입니다 token -> {}", token);
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
   }
}