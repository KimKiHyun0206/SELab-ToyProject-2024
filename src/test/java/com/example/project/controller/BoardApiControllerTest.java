package com.example.project.controller;

import com.example.project.board.dto.request.BoardDeleteRequest;
import com.example.project.board.dto.request.BoardReadRequest;
import com.example.project.board.dto.request.BoardRegisterRequest;
import com.example.project.board.dto.request.BoardUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegister() throws Exception {
        long startTime = System.currentTimeMillis();
        for (long i = 1; i <= 100; i++) {
            BoardRegisterRequest boardRegisterRequest = new BoardRegisterRequest();
            boardRegisterRequest.setTitle("Test Title " + i);
            boardRegisterRequest.setContext("Test Context " + i);
            boardRegisterRequest.setUserId(i);
            boardRegisterRequest.setSolutionId(i);

            String boardJson = objectMapper.writeValueAsString(boardRegisterRequest);

            mockMvc.perform(post("/api/board")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(boardJson))
                    .andExpect(status().isCreated());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken for 100 register tests: " + (endTime - startTime) + "ms");
    }

    @Test
    public void testRead() throws Exception {

        testRegister();

        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 100; i++) {
            BoardReadRequest boardReadRequest = new BoardReadRequest();
            boardReadRequest.setBoardId((long) i);

            String boardJson = objectMapper.writeValueAsString(boardReadRequest);

            mockMvc.perform(get("/api/board")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(boardJson))
                    .andExpect(status().isOk());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken for 100 read tests: " + (endTime - startTime) + "ms");
    }

    @Test
    public void testUpdate() throws Exception {

        testRegister();

        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 100; i++) {
            BoardUpdateRequest boardUpdateRequest = new BoardUpdateRequest();
            boardUpdateRequest.setId((long) i);
            boardUpdateRequest.setUserId((long) i);
            boardUpdateRequest.setTitle("Updated Title " + i);
            boardUpdateRequest.setContext("Updated Context " + i);

            String updateJson = objectMapper.writeValueAsString(boardUpdateRequest);

            mockMvc.perform(put("/api/board")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updateJson))
                    .andExpect(status().isOk());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken for 100 update tests: " + (endTime - startTime) + "ms");
    }


    @Test
    public void testDelete() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 100; i++) {
            BoardDeleteRequest boardDeleteRequest = new BoardDeleteRequest();
            boardDeleteRequest.setBoardId((long) i);
            boardDeleteRequest.setUserId((long) i);

            String deleteJson = objectMapper.writeValueAsString(boardDeleteRequest);

            mockMvc.perform(delete("/api/board")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(deleteJson))
                    .andExpect(status().isOk());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken for 100 delete tests: " + (endTime - startTime) + "ms");
    }
}
