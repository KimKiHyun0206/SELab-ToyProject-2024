package com.example.project.user.controller.ui;

import com.example.project.auth.domain.UserDetail;
import com.example.project.error.exception.user.InvalidLoginUserIdException;
import com.example.project.error.exception.user.InvalidLoginPasswordException;
import com.example.project.user.controller.UserApiController;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.LoginService;
import com.example.project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserApiController userApiController;
    private final LoginService loginService;
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("LoginRequest", new LoginRequest());

        return "/non-authentication/user/login";
    }

    //TODO 쿠키를 남긴다
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, Model model) {
        log.info("[ SYSTEM ] Login Tried ID : {}", loginRequest.getUserId());
        log.info("[ SYSTEM ] Login Tried PASSWORD : {}", loginRequest.getPassword());

        try {
            UserResponse login = loginService.login(loginRequest);
        } catch (InvalidLoginUserIdException e) {
            log.info("[ SYSTEM ] ID가 일치하지 않습니다");

            model.addAttribute("IdError", "입력한 ID " + loginRequest.getUserId() + "가 존재하지 않습니다");

            return "/non-authentication/user/login";
        } catch (InvalidLoginPasswordException e) {
            log.info("[ SYSTEM ] PASSWORD가 일치하지 않습니다");

            model.addAttribute("PasswordError", "입력한 PASSWORD " + loginRequest.getPassword() + "가 존재하지 않습니다");

            return "/non-authentication/user/login";
        }

        return null;
    }

    //TODO 토큰 인증으로 마이페이지 접속할 수 있도록 추가하기
    @GetMapping("/my-page/{id}")
    public String myPage(Model model, @PathVariable Long id) {
        var userResponse = userService.read(id);
        log.info("[ SYSTEM ] MyPage user 조회 성공했습니다 {}", id);
        model.addAttribute("UserInfo", userResponse);

        return "/authentication/user/info/info";
    }

    //TODO 토큰 인증으로 유저 정보 수정 가능하도록 만들기
    @GetMapping("/edit/{id}")
    public String editInfo(Model model, @PathVariable Long id){
        var userResponse = userService.read(id);
        log.info("[ SYSTEM ] Edit user 조회 성공했습니다 {}", id);
        model.addAttribute("UserResponse", userResponse);
        model.addAttribute("UpdateRequest", new UserUpdateRequest());

        return "/authentication/user/info/edit_info";
    }

    //TODO 토큰 인증으로 유저 정보 수정하도록 만들기
    @PostMapping("/edit/{id}")
    public String edit(UserUpdateRequest request, Model model, @PathVariable Long id){
        UserResponse userResponse = userService.read(id);
        UserDetail userDetail = new UserDetail(userResponse.toEntity());
        var edit = userService.updateUser(userDetail, request);

        model.addAttribute("UserInfo", edit);

        return "/authentication/user/info/info";
    }
}
