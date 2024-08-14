package com.example.project.controller;

import com.example.project.compile.controller.CCompileController;
import com.example.project.compile.service.CCompileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CCompileController.class)
class CCompileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CCompileService cCompileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteCCodeSuccess() throws Exception {
        String cCode = "#include <stdio.h>\nint main() { printf(\"Hello, World!\\n\"); return 0; }";
        String expectedOutput = "Hello, World!\n";
        given(cCompileService.executeCCode(anyString())).willReturn(expectedOutput);

        mockMvc.perform(post("/api/ccode/execute")
                        .content(cCode)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedOutput));
    }

    @Test
    void testExecuteCCodeFailure() throws Exception {
        String cCode = "#include <stdio.h>\nint main() { printf(\"Hello, World!\\n\" return 0; }";  // Syntax error
        given(cCompileService.executeCCode(anyString())).willThrow(new IOException("Compilation Error"));

        mockMvc.perform(post("/api/ccode/execute")
                        .content(cCode)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error executing C code: Compilation Error"));
    }
}
