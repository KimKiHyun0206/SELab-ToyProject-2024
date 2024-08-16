package com.example.project.controller;

import com.example.project.solution.controller.ui.SolutionController;
import com.example.project.solution.dto.SolutionResponse;
import com.example.project.solution.dto.request.admin.SolutionDeleteRequest;
import com.example.project.solution.service.AdminSolutionService;
import com.example.project.solution.service.UserSolutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(SolutionController.class)
public class SolutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminSolutionService adminSolutionService;

    @MockBean
    private UserSolutionService userSolutionService;

    @BeforeEach
    public void setUp() {
        given(userSolutionService.read(any())).willReturn(
                SolutionResponse.builder()
                        .id(1L)
                        .title("Sample Title")
                        .description("Sample Description")
                        .build()
        );

        given(adminSolutionService.delete(any(SolutionDeleteRequest.class))).willReturn(
                SolutionResponse.builder()
                        .id(1L)
                        .title("Deleted Title")
                        .description("Deleted Description")
                        .build()
        );
    }

    @Test
    public void testSolutionControllerPerformance() throws Exception {
        int iterations = 100;
        long totalTime = 0;

        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();

            mockMvc.perform(MockMvcRequestBuilders.get("/api/solution")
                            .param("solutionId", "1"))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            totalTime += executionTime;

            System.out.println("반복 " + (i + 1) + " 실행 시간: " + executionTime + "ms");
        }

        double averageTime = (double) totalTime / iterations;
        System.out.println("평균 실행 시간: " + averageTime + "ms");
    }
}