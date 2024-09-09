package com.example.project.user.controller;

import com.example.project.error.exception.user.InvalidLoginUserIdException;
import com.example.project.error.exception.user.InvalidLoginPasswordException;
import com.example.project.jwt.token.TokenProvider;
import com.example.project.jwt.token.TokenResolver;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.service.CookieService;
import com.example.project.user.service.SessionService;
import com.example.project.user.service.LoginService;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserLoginController {
    private final LoginService loginService;
    private final SessionService sessionService;
    private final CookieService cookieService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;


    public void jwtAuthLogin(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse httpServletResponse
    ) throws IOException {
        log.info("jwtAuthLogin {}, {}", loginRequest.getUserId(), loginRequest.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        log.info("authrize jwt {}", jwt);

        httpServletResponse.addHeader(TokenResolver.AUTHORIZATION_HEADER, "Bearer " + jwt);
        httpServletResponse.sendRedirect("http://localhost:8080/");
    }

    @PostMapping("/login")
    public void login(
            @RequestBody LoginRequest loginRequest,
            Model model,
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        log.info("Login Try -> id : {}, password : {}", loginRequest.getUserId(), loginRequest.getPassword());

        try {
            UserResponse userResponse;

            if (cookieValue == null) userResponse = noCookieLogin(loginRequest);
            else userResponse = cookieLogin(loginRequest, cookieValue, request);

            sessionService.sessionRegistration(request, userResponse);
            response.addCookie(cookieService.authCookieIssue(loginRequest));
            //TODO loginInfo cookie 반환하도록 수정
            response.addCookie(cookieService.loginInfoCookieIssue(loginRequest));
            response.sendRedirect("http://localhost:8080/");

        } catch (InvalidLoginUserIdException e) {
            log.info(e.getMessage());
            model.addAttribute("IdError", "입력한 ID " + loginRequest.getUserId() + "가 존재하지 않습니다");
            response.sendRedirect("http://localhost:8080/user/login");

        } catch (InvalidLoginPasswordException e) {
            log.info(e.getMessage());
            model.addAttribute("PasswordError", "입력한 PASSWORD " + loginRequest.getPassword() + "가 존재하지 않습니다");
            response.sendRedirect("http://localhost:8080/user/login");
        }
    }

    private UserResponse noCookieLogin(
            LoginRequest loginRequest
    ) throws InvalidLoginUserIdException, InvalidLoginPasswordException {
        return loginService.login(loginRequest.getUserId(), loginRequest.getPassword());
    }

    private UserResponse cookieLogin(LoginRequest loginRequest, String cookieValue, HttpServletRequest request) throws InvalidLoginUserIdException, InvalidLoginPasswordException {
        var userResponse = sessionService.getUser(request, cookieValue);

        if (userResponse == null) {
            return loginService.login(loginRequest.getUserId(), loginRequest.getPassword());
        }

        return userResponse;
    }
}