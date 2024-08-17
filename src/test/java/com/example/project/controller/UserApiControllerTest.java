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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserApiController.class)
public class UserApiControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserApiControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Setup remains largely the same
    }

    @Test
    public void checkEmailForSignUp() throws Exception {
        List<String> emails = Arrays.asList("test1@example.com", "test2@example.com", "invalid-email");

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            String email = emails.get(i % emails.size());
            boolean isValid = email.contains("@");

            if (isValid) {
                doNothing().when(userService).duplicateValidationUserEmail(email);
            } else {
                doThrow(new IllegalArgumentException("Invalid email format")).when(userService).duplicateValidationUserEmail(email);
            }

            mockMvc.perform(get("/api/v1/user/email-check")
                            .param("email", email))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(isValid ? "SUCCESS_SIGN_UP_EMAIL_CHECK" : "INVALID_EMAIL_FORMAT"));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time for checkEmailForSignUp (100 iterations): " + (endTime - startTime) + " ms");
    }

    @Test
    public void joinMember() throws Exception {
        List<UserRegisterRequest> requests = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            UserRegisterRequest request = new UserRegisterRequest();
            request.setUserId("user" + i);
            request.setPassword("password" + i);
            request.setName("User " + i);
            request.setEmail("user" + i + "@example.com");
            request.setPoint(0L);
            request.setRoleType(RoleType.USER);
            requests.add(request);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            UserRegisterRequest request = requests.get(i);
            UserResponse response = UserResponse.builder()
                    .id((long) i)
                    .userId(request.getUserId())
                    .password("encodedPassword" + i)
                    .name(new Name(request.getName()))
                    .point(request.getPoint())
                    .email(new Email(request.getEmail()))
                    .roleType(request.getRoleType())
                    .build();

            when(userService.register(any(UserRegisterRequest.class))).thenReturn(response);

            mockMvc.perform(post("/api/v1/user/signup")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("CREATE_SUCCESS_USER"))
                    .andExpect(jsonPath("$.data.id").value(i))
                    .andExpect(jsonPath("$.data.userId").value(request.getUserId()));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time for joinMember (100 iterations): " + (endTime - startTime) + " ms");
    }

    @Test
    public void editMember() throws Exception {
        List<UserUpdateRequest> requests = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            UserUpdateRequest request = new UserUpdateRequest();
            request.setUserId("user" + i);
            request.setPassword("newPassword" + i);
            request.setName("Updated User " + i);
            request.setEmail("updated" + i + "@example.com");
            requests.add(request);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            UserUpdateRequest request = requests.get(i);
            UserResponse response = UserResponse.builder()
                    .id((long) i)
                    .userId(request.getUserId())
                    .password("newEncodedPassword" + i)
                    .name(new Name(request.getName()))
                    .point(0L)
                    .email(new Email(request.getEmail()))
                    .roleType(RoleType.USER)
                    .build();

            when(userService.updateUser(any(UserDetail.class), any(UserUpdateRequest.class))).thenReturn(response);

            mockMvc.perform(patch("/api/v1/user/edit")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("SUCCESS_UPDATE_USER"))
                    .andExpect(jsonPath("$.data.userId").value(request.getUserId()))
                    .andExpect(jsonPath("$.data.name").value(request.getName()))
                    .andExpect(jsonPath("$.data.email").value(request.getEmail()));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time for editMember (100 iterations): " + (endTime - startTime) + " ms");
    }

    @Test
    public void searchAllMember() {
        List<UserResponse> allUsers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            allUsers.add(UserResponse.builder()
                    .id((long) i)
                    .userId("user" + i)
                    .password("encodedPassword" + i)
                    .name(new Name("User " + i))
                    .point((long) (i * 10))
                    .email(new Email("user" + i + "@example.com"))
                    .roleType(RoleType.USER)
                    .build());
        }

        when(userService.readAllUser()).thenReturn(allUsers);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            try {
                mockMvc.perform(get("/api/v1/user"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.message").value("SUCCESS_SEARCH_ALL_USER"))
                        .andExpect(jsonPath("$.data").isArray())
                        .andExpect(jsonPath("$.data.length()").value(100))
                        .andExpect(jsonPath("$.data[0].userId").value("user0"))
                        .andExpect(jsonPath("$.data[99].userId").value("user99"));
            } catch (Exception e) {
                logger.error("Error during searchAllMember test", e);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time for searchAllMember (100 iterations): " + (endTime - startTime) + " ms");
    }
}