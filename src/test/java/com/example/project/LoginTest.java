package com.example.project;

import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.service.LoginService;
import com.example.project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
public class LoginTest {

    private final UserService userService;
    private final LoginService loginService;

    @Test
    public void loginTest(){
        LoginRequest loginRequest = new LoginRequest(
                "kim",
                "abc"
        );

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(
                "kim",
                "abc",
                "kkh",
                "rlarlgus0206@naver.com"
        );

        UserResponse register = userService.register(userRegisterRequest);

        log.info(register.getPassword());

        UserResponse login = loginService.login(loginRequest);

        log.info(login.getPassword());
    }
}
