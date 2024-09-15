package com.example.project.user.controller.rest;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.common.util.HeaderUtil;
import com.example.project.user.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserLogoutController {
    private final AuthTokenService authTokenService;
    private final CookieService cookieService;

    @DeleteMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String token = HeaderUtil.resolveToken(httpServletRequest);

        if(authTokenService.isValidateToken(token)){
            authTokenService.deleteToken(token);
            httpServletResponse.addCookie(cookieService.deleteCookie());
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
        }else {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }
}
