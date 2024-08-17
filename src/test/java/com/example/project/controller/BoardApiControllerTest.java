package com.example.project.controller;

import com.example.project.board.dto.BoardResponse;
import com.example.project.board.dto.request.BoardDeleteRequest;
import com.example.project.board.dto.request.BoardRegisterRequest;
import com.example.project.board.dto.request.BoardUpdateRequest;
import com.example.project.board.service.BoardService;
import com.example.project.board.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void setup() {
        boardRepository.deleteAll();

        BoardRegisterRequest initialBoard = new BoardRegisterRequest();
        initialBoard.setTitle("Initial Board");
        initialBoard.setContext("Initial Context");
        initialBoard.setUserId(1L);
        initialBoard.setSolutionId(1L);

        BoardResponse response = boardService.register(initialBoard);

        Assertions.assertNotNull(response, "초기 게시글 등록 실패");
        Assertions.assertEquals("Initial Board", response.getTitle(), "초기 게시글 제목 불일치");
    }

    @Test
    public void testRegisterReadUpdateDelete() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= 100; i++) {
            BoardRegisterRequest registerRequest = new BoardRegisterRequest();
            registerRequest.setTitle("Test Title " + i);
            registerRequest.setContext("Test Context " + i);
            registerRequest.setUserId((long) i);
            registerRequest.setSolutionId((long) i);

            String registerJson = objectMapper.writeValueAsString(registerRequest);

            mockMvc.perform(post("/api/board")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(registerJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.title", is("Test Title " + i)))
                    .andExpect(jsonPath("$.context", is("Test Context " + i)));

            mockMvc.perform(get("/api/board/" + (i + 1)) // i + 1 because we have an initial board
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title", is("Test Title " + i)))
                    .andExpect(jsonPath("$.context", is("Test Context " + i)));

            BoardUpdateRequest updateRequest = new BoardUpdateRequest();
            updateRequest.setId((long) (i + 1));
            updateRequest.setUserId((long) i);
            updateRequest.setTitle("Updated Title " + i);
            updateRequest.setContext("Updated Context " + i);

            String updateJson = objectMapper.writeValueAsString(updateRequest);

            mockMvc.perform(put("/api/board")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updateJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title", is("Updated Title " + i)))
                    .andExpect(jsonPath("$.context", is("Updated Context " + i)));

            BoardDeleteRequest deleteRequest = new BoardDeleteRequest();
            deleteRequest.setBoardId((long) (i + 1));
            deleteRequest.setUserId((long) i);

            String deleteJson = objectMapper.writeValueAsString(deleteRequest);

            mockMvc.perform(delete("/api/board")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(deleteJson))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/api/board/" + (i + 1))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("시간 : " + (endTime - startTime) + "ms");
    }
}