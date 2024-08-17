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
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @Test
    void testCommentControllerFunctionalityAndPerformance() throws Exception {
        CommentRegisterRequest registerRequest = new CommentRegisterRequest();
        registerRequest.setBoardId(1L);
        registerRequest.setUserId(1L);
        registerRequest.setComment("Test comment");

        CommentResponse commentResponse = new CommentResponse(1L, 1L, 1L, "Test comment");
        List<CommentResponse> commentList = Arrays.asList(
                new CommentResponse(1L, 1L, 1L, "First comment"),
                new CommentResponse(2L, 1L, 2L, "Second comment")
        );

        when(commentService.register(any(CommentRegisterRequest.class))).thenReturn(commentResponse);
        when(commentService.get(1L)).thenReturn(commentResponse);
        when(commentService.getAll(1L)).thenReturn(commentList);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            MvcResult postResult = mockMvc.perform(post("/comment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.comment").value("Test comment"))
                    .andReturn();

            CommentResponse createdComment = objectMapper.readValue(postResult.getResponse().getContentAsString(), CommentResponse.class);
            assertNotNull(createdComment.getId());

            mockMvc.perform(get("/comment/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.comment").value("Test comment"));

            MvcResult getAllResult = mockMvc.perform(get("/comment/all/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andReturn();

            List<CommentResponse> retrievedComments = Arrays.asList(objectMapper.readValue(getAllResult.getResponse().getContentAsString(), CommentResponse[].class));
            assertEquals(2, retrievedComments.size());
            assertEquals("First comment", retrievedComments.get(0).getComment());
            assertEquals("Second comment", retrievedComments.get(1).getComment());
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Total time for 100 iterations: " + totalTime + " ms");
    }
}