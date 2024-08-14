package com.example.project.compile.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class JavaCompileService {

    private static final String TEMP_CLASS_NAME = "TempClass";
    private static final String TEMP_FILE_NAME = TEMP_CLASS_NAME + ".java";

    public String executeJavaCode(String javaCode) {
        String javaFileContent = createJavaFileContent(javaCode);
        writeJavaFile(javaFileContent);

        int compileExitStatus = compileJavaCode();
        if (compileExitStatus != 0) {
            return "Compilation error.";
        }

        String executionOutput = runJavaCode();
        if (executionOutput == null) {
            return "Execution error.";
        }

        return executionOutput;
    }

    private String createJavaFileContent(String javaCode) {
        return "public class " + TEMP_CLASS_NAME + " { public static void main(String[] args) { " + javaCode + " } }";
    }

    private void writeJavaFile(String javaFileContent) {
        try {
            Files.write(Paths.get(TEMP_FILE_NAME), javaFileContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error writing java file: " + e.getMessage(), e);
        }
    }

    private int compileJavaCode() {
        try {
            Process compileProcess = new ProcessBuilder("javac", TEMP_FILE_NAME).start();
            return compileProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error during compilation: " + e.getMessage(), e);
        }
    }

    private String runJavaCode() {
        try {
            Process runProcess = new ProcessBuilder("java", TEMP_CLASS_NAME).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitStatus = runProcess.waitFor();
            return exitStatus == 0 ? output.toString() : null;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error during execution: " + e.getMessage(), e);
        }
    }
}