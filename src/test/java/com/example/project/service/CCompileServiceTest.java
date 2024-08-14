package com.example.project.service;

import com.example.project.compile.service.CCompileService;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class CCompileServiceTest {

    private final CCompileService cCompileService = new CCompileService();

    @Test
    void testExecuteCCodeSuccess() throws IOException, InterruptedException {
        String cCode = "#include <stdio.h>\nint main() { printf(\"Hello, World!\\n\"); return 0; }";
        String output = cCompileService.executeCCode(cCode);
        assertEquals("Hello, World!\n", output);
    }

    @Test
    void testExecuteCCodeCompilationError() throws IOException, InterruptedException {
        String cCode = "#include <stdio.h>\nint main() { printf(\"Hello, World!\\n\" return 0; }";  // Missing semicolon
        String output = cCompileService.executeCCode(cCode);
        assertTrue(output.contains("Compilation Error:"));
    }
}
