package com.example.project.controller;

import com.example.project.compile.controller.JavaCompileController;
import com.example.project.compile.service.JavaCompileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(JavaCompileController.class)
public class JavaCompileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JavaCompileService executionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteJavaCode() throws Exception {
        String javaCode = "public class Test { public static void main(String[] args) { System.out.println(\"Hello World\"); } }";
        String expectedResponse = "Execution Result";

        when(executionService.executeJavaCode(javaCode)).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(javaCode))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}
