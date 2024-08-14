package com.example.project.service;

import com.example.project.compile.service.JavaCompileService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExecutionServiceTests {

    private final JavaCompileService javaCompileService = new JavaCompileService();

    @Test
    void testExecuteJavaCodeSuccess() {
        String javaCode = "System.out.println(\"Hello, World!\");";
        String result = javaCompileService.executeJavaCode(javaCode);
        assertTrue(result.contains("Hello, World!"), "The output should contain 'Hello, World!'");
    }

    @Test
    void testExecuteJavaCodeCompilationError() {
        String javaCode = "System.out.prntln(\"Hello, World!\");";
        String result = javaCompileService.executeJavaCode(javaCode);
        assertEquals("Compilation error.", result, "The output should indicate a compilation error.");
    }

    @Test
    void testExecuteJavaCodeExecutionError() {
        String javaCode = "int a = 1 / 0;";
        String result = javaCompileService.executeJavaCode(javaCode);
        assertEquals("Execution error.", result, "The output should indicate an execution error.");
    }
}
