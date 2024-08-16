package com.example.project.user.controller;

import com.example.project.auth.domain.UserDetail;
import com.example.project.error.exception.user.InvalidLoginUserIdException;
import com.example.project.error.exception.user.InvalidLoginPasswordException;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.CookieService;
import com.example.project.user.service.SessionService;
import com.example.project.user.service.LoginService;
import com.example.project.user.service.UserService;
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
    private final SessionService sessionService;
    private final CookieService cookieService;


    @PostMapping("/login")
    public void login(
            LoginRequest loginRequest,
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
        return loginService.login(loginRequest);
    }

    private UserResponse cookieLogin(LoginRequest loginRequest, String cookieValue, HttpServletRequest request) throws InvalidLoginUserIdException, InvalidLoginPasswordException {
        var userResponse = sessionService.checkSession(request, cookieValue);

        if (userResponse == null) {
            return loginService.login(loginRequest);
        }

        return userResponse;
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