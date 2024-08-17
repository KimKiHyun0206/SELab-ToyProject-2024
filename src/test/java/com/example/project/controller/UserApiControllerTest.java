package com.example.project.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.project.user.controller.UserApiController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.example.project.user.service.UserService;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.auth.domain.UserDetail;
import com.example.project.user.domain.vo.Email;
import com.example.project.user.domain.vo.Name;
import com.example.project.user.domain.vo.RoleType;
import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserApiController.class)
public class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        doNothing().when(userService).duplicateValidationUserEmail(anyString());

        when(userService.register(any(UserRegisterRequest.class)))
                .thenReturn(UserResponse.builder()
                        .id(1L)
                        .userId("testUser")
                        .password("encodedPassword")
                        .name(new Name("Test User"))
                        .point(0L)
                        .email(new Email("test@example.com"))
                        .roleType(RoleType.USER)
                        .build());

        when(userService.updateUser(any(UserDetail.class), any(UserUpdateRequest.class)))
                .thenReturn(UserResponse.builder()
                        .id(1L)
                        .userId("updatedUser")
                        .password("newEncodedPassword")
                        .name(new Name("Updated User"))
                        .point(0L)
                        .email(new Email("updated@example.com"))
                        .roleType(RoleType.USER)
                        .build());

        when(userService.readAllUser())
                .thenReturn(Arrays.asList(
                        UserResponse.builder()
                                .id(1L)
                                .userId("user1")
                                .password("encodedPassword1")
                                .name(new Name("User One"))
                                .point(10L)
                                .email(new Email("user1@example.com"))
                                .roleType(RoleType.USER)
                                .build(),
                        UserResponse.builder()
                                .id(2L)
                                .userId("user2")
                                .password("encodedPassword2")
                                .name(new Name("User Two"))
                                .point(20L)
                                .email(new Email("user2@example.com"))
                                .roleType(RoleType.USER)
                                .build()
                ));
    }

    @Test
    public void checkEmailForSignUp() throws Exception {
        for (int i = 0; i < 100; i++) {
            long startTime = System.currentTimeMillis();

            mockMvc.perform(get("/api/v1/user/email-check")
                            .param("email", "test@example.com"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"message\":\"SUCCESS_SIGN_UP_EMAIL_CHECK\", \"data\":true}"));

            long endTime = System.currentTimeMillis();
            System.out.println("Execution time for checkEmailForSignUp iteration " + i + ": " + (endTime - startTime) + "ms");
        }
        verify(userService, times(100)).duplicateValidationUserEmail(anyString());
    }

    @Test
    public void joinMember() throws Exception {
        String userJson = "{\"userId\":\"testUser\", \"password\":\"password123\", \"name\":\"Test User\", \"email\":\"test@example.com\", \"point\":0, \"roleType\":\"USER\"}";

        for (int i = 0; i < 100; i++) {
            long startTime = System.currentTimeMillis();

            mockMvc.perform(post("/api/v1/user/signup")
                            .contentType("application/json")
                            .content(userJson))
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"message\":\"CREATE_SUCCESS_USER\"}"));

            long endTime = System.currentTimeMillis();
            System.out.println("회원가입 실행 시간 (반복 " + i + "): " + (endTime - startTime) + "ms");
        }
        verify(userService, times(100)).register(any(UserRegisterRequest.class));
    }

    @Test
    public void editMember() throws Exception {
        String userUpdateJson = "{\"userId\":\"testUser\", \"password\":\"newPassword123\", \"name\":\"Updated User\", \"email\":\"test2@example.com\"}";

        for (int i = 0; i < 100; i++) {
            long startTime = System.currentTimeMillis();

            mockMvc.perform(patch("/api/v1/user/edit")
                            .contentType("application/json")
                            .content(userUpdateJson))
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"message\":\"SUCCESS_UPDATE_USER\"}"));

            long endTime = System.currentTimeMillis();
            System.out.println("회원 정보 수정 실행 시간 (반복 " + i + "): " + (endTime - startTime) + "ms");
        }
        verify(userService, times(100)).updateUser(any(UserDetail.class), any(UserUpdateRequest.class));
    }

    @Test
    public void searchAllMember() throws Exception {
        for (int i = 0; i < 100; i++) {
            long startTime = System.currentTimeMillis();

            mockMvc.perform(get("/api/v1/user"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"message\":\"SUCCESS_SEARCH_ALL_USER\"}"));

            long endTime = System.currentTimeMillis();
            System.out.println("전체 회원 조회 실행 시간 (반복 " + i + "): " + (endTime - startTime) + "ms");
        }
        verify(userService, times(100)).readAllUser();
    }
}