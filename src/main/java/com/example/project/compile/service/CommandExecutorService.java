package com.example.project.compile.service;

import com.example.project.error.dto.ErrorMessage;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class CommandExecutorService {

    public String runCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

        if (process.exitValue() == 0) {
            return readStream(process.getInputStream());
        } else {
            String errorOutput = readStream(process.getErrorStream());
            throw new IOException(ErrorMessage.EXECUTION_FAILED.getMessage() + "\n" + errorOutput);
        }
    }

    private String readStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new IOException(ErrorMessage.EXECUTION_FAILED.getMessage() + "\n" + e.getMessage());
        }
        return output.toString();
    }
}