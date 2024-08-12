package com.example.project.user.controller.ui;

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

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final LoginService loginService;
    private final UserService userService;
    private final LoginAuthService loginAuthService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("LoginRequest", new LoginRequest());

        return "/non-authentication/user/login";
    }


    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest loginRequest,
            Model model,
            @CookieValue("DigitalLoginCookie") String cookieValue,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (cookieValue == null) {    //쿠키가 없는 경우
            try {
                var userResponse = loginService.login(loginRequest);
                Cookie cookie = loginAuthService.cookieIssuance(loginRequest);//성공할 시 쿠키 발급
                response.addCookie(cookie);
                loginAuthService.sessionRegistration(request, userResponse);         //성공할 시 세션 발급
                return "/"; //성공할 경우 쿠키를 가지고 메인 페이지로 돌아감
            } catch (InvalidLoginUserIdException e) {
                model.addAttribute("IdError", "입력한 ID " + loginRequest.getUserId() + "가 존재하지 않습니다");
                return "/non-authentication/user/login";
            } catch (InvalidLoginPasswordException e) {
                model.addAttribute("PasswordError", "입력한 PASSWORD " + loginRequest.getPassword() + "가 존재하지 않습니다");
                return "/non-authentication/user/login";
            }
        } else { //쿠키가 있는 경우
            var userResponse = loginAuthService.checkSession(request, cookieValue);
            if (userResponse == null) { //만약 세션과 쿠키가 일치하지 않을 경우
                return "/non-authentication/user/login";
            }
            Cookie cookie = loginAuthService.cookieIssuance(loginRequest);//성공할 시 쿠키 발급
            response.addCookie(cookie);
            return "/"; //성공할 경우 main 페이지로
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
