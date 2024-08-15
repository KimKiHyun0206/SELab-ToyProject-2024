package com.example.project.controller;

import com.example.project.comment.controller.CommentController;
import com.example.project.comment.dto.CommentRegisterRequest;
import com.example.project.comment.dto.CommentResponse;
import com.example.project.comment.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @Test
    void testCommentControllerPerformance() throws Exception {
        CommentRegisterRequest registerRequest = new CommentRegisterRequest();
        registerRequest.setBoardId(1L);
        registerRequest.setUserId(1L);
        registerRequest.setComment(".");

        CommentResponse commentResponse = new CommentResponse(1L, 1L, 1L, ".");

        when(commentService.register(any(CommentRegisterRequest.class))).thenReturn(commentResponse);
        when(commentService.get(any(Long.class))).thenReturn(commentResponse);
        when(commentService.getAll(any(Long.class))).thenReturn(Collections.singletonList(commentResponse));

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            mockMvc.perform(MockMvcRequestBuilders.post("/comment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.comment", is(".")));

            mockMvc.perform(MockMvcRequestBuilders.get("/comment/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.comment", is(".")));

            mockMvc.perform(MockMvcRequestBuilders.get("/comment/all/1"))
                    .andExpect(status().isOk());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("100번 반복에 대한 총 소요 시간: " + (endTime - startTime) + " ms");
    }
}