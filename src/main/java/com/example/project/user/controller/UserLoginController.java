package com.example.project.user.controller;

import com.example.project.auth.domain.UserDetail;
import com.example.project.error.exception.user.InvalidLoginUserIdException;
import com.example.project.error.exception.user.InvalidLoginPasswordException;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.LoginAuthService;
import com.example.project.user.service.LoginService;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserLoginController {
    private final LoginService loginService;
    private final UserService userService;
    private final LoginAuthService userAuthService;


    @PostMapping("/login")
    public void login(
            LoginRequest loginRequest,
            Model model,
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        log.info("id : {}, password : {}",loginRequest.getUserId(), loginRequest.getPassword());


        if (cookieValue == null) {    //쿠키가 없는 경우
            try {
                var userResponse = loginService.login(loginRequest);
                Cookie cookie = userAuthService.cookieIssuance(loginRequest); // 성공 시 쿠키 발급
                response.addCookie(cookie);
                userAuthService.sessionRegistration(request, userResponse);   // 성공 시 세션 발급
                log.info("Login successful");
                response.sendRedirect("http://localhost:8080/"); // 성공 시 메인 페이지로 리다이렉트

            } catch (InvalidLoginUserIdException e) {
                log.info(e.getMessage());
                model.addAttribute("IdError", "입력한 ID " + loginRequest.getUserId() + "가 존재하지 않습니다");
                response.sendRedirect("http://localhost:8080/user/login");

            } catch (InvalidLoginPasswordException e) {
                log.info(e.getMessage());
                model.addAttribute("PasswordError", "입력한 PASSWORD " + loginRequest.getPassword() + "가 존재하지 않습니다");
                response.sendRedirect("http://localhost:8080/user/login");
            }
        } else { //쿠키가 있는 경우
            var userResponse = userAuthService.checkSession(request, cookieValue);
            log.info("request: " + request);
            log.info("cookieValue: " + cookieValue);
            if (userResponse == null) { //만약 세션과 쿠키가 일치하지 않을 경우
                log.info("Login failed");
                response.sendRedirect("http://localhost:8080/user/login");
            }
            log.info("Login session successful");
            response.sendRedirect("http://localhost:8080/"); //성공할 경우 main 페이지로
        }
    }


    //TODO 토큰 인증으로 마이페이지 접속할 수 있도록 추가하기
    @GetMapping("/my-page/{id}")
    public String myPage(Model model, @PathVariable Long id) throws ExecutionException, InterruptedException {
        var userResponse = userService.find(id);
        log.info("[ SYSTEM ] MyPage user 조회 성공했습니다 {}", id);
        model.addAttribute("UserInfo", userResponse);

        return "/authentication/user/info/info";
    }

    //TODO 토큰 인증으로 유저 정보 수정 가능하도록 만들기
    @GetMapping("/edit/{id}")
    public String editInfo(Model model, @PathVariable Long id) throws ExecutionException, InterruptedException {
        var userResponse = userService.find(id);
        log.info("[ SYSTEM ] Edit user 조회 성공했습니다 {}", id);
        model.addAttribute("UserResponse", userResponse);
        model.addAttribute("UpdateRequest", new UserUpdateRequest());

        return "/authentication/user/info/edit_info";
    }

    //TODO 토큰 인증으로 유저 정보 수정하도록 만들기
    @PostMapping("/edit/{id}")
    public String edit(UserUpdateRequest request, Model model, @PathVariable Long id) throws ExecutionException, InterruptedException {
        var userResponse = userService.find(id);
        UserDetail userDetail = new UserDetail(userResponse.toEntity());
        var edit = userService.updateUser(userDetail, request);

        model.addAttribute("UserInfo", edit);

        return "/authentication/user/info/info";
    }
}