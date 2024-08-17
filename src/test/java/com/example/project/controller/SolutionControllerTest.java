package com.example.project.controller;

import com.example.project.solution.controller.ui.SolutionController;
import com.example.project.solution.dto.SolutionResponse;
import com.example.project.solution.dto.request.admin.SolutionDeleteRequest;
import com.example.project.solution.service.AdminSolutionService;
import com.example.project.solution.service.UserSolutionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SolutionController.class)
public class SolutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminSolutionService adminSolutionService;

    @MockBean
    private UserSolutionService userSolutionService;

    private void performTestMultipleTimes(String testName, TestOperation operation) throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            ResultActions result = operation.perform();
            result.andExpect(status().isOk()); // 각 실행마다 상태 확인
        }

        long endTime = System.currentTimeMillis();
        long totalTimeInMs = endTime - startTime;

        System.out.printf("%s ,시간 %d ms%n", testName, totalTimeInMs);
    }

    @FunctionalInterface
    private interface TestOperation {
        ResultActions perform() throws Exception;
    }

    @Test
    public void testReadSolution() throws Exception {
        String solutionId = "1";
        SolutionResponse mockResponse = SolutionResponse.builder()
                .id(1L)
                .title("Sample Title")
                .description("Sample Description")
                .build();

        given(userSolutionService.read(any())).willReturn(mockResponse);

        performTestMultipleTimes("Read Solution", () ->
                mockMvc.perform(get("/api/solution")
                                .param("solutionId", solutionId))
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.title").value("Sample Title"))
                        .andExpect(jsonPath("$.description").value("Sample Description"))
        );
    }

    @Test
    public void testDeleteSolution() throws Exception {
        String adminId = "admin123";
        Long solutionId = 1L;
        SolutionDeleteRequest deleteRequest = new SolutionDeleteRequest();
        deleteRequest.setAdminId(adminId);
        deleteRequest.setSolutionId(solutionId);

        SolutionResponse mockResponse = SolutionResponse.builder()
                .id(solutionId)
                .title("Deleted Title")
                .description("Deleted Description")
                .build();

        given(adminSolutionService.delete(any(SolutionDeleteRequest.class))).willReturn(mockResponse);

        performTestMultipleTimes("Delete Solution", () ->
                mockMvc.perform(delete("/api/admin/solution")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(deleteRequest)))
                        .andExpect(jsonPath("$.id").value(solutionId))
                        .andExpect(jsonPath("$.title").value("Deleted Title"))
                        .andExpect(jsonPath("$.description").value("Deleted Description"))
        );
    }

    @Test
    public void testReadSolutionNotFound() throws Exception {
        String nonExistentId = "999";
        given(userSolutionService.read(any())).willThrow(new RuntimeException("Solution not found"));

        performTestMultipleTimes("Read Solution Not Found", () ->
                mockMvc.perform(get("/api/solution")
                                .param("solutionId", nonExistentId))
                        .andExpect(status().isNotFound())
        );
    }
}