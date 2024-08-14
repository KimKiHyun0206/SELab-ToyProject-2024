package com.example.project.compile.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CCompileService {

    public String executeCCode(String cCode) throws IOException, InterruptedException {

        File sourceFile = File.createTempFile("temp", ".c");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sourceFile))) {
            writer.write(cCode);
        }

        ProcessBuilder compileBuilder = new ProcessBuilder("gcc",
                sourceFile.getAbsolutePath(), "-o",
                sourceFile.getAbsolutePath() + ".out");
        Process compileProcess = compileBuilder.start();
        compileProcess.waitFor();

        if (compileProcess.exitValue() != 0) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            String line;
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }
            return "Compilation Error:\n" + errorOutput.toString();
        }

        ProcessBuilder executeBuilder =
                new ProcessBuilder(sourceFile.getAbsolutePath() + ".out");
        Process executeProcess = executeBuilder.start();

        BufferedReader outputReader =
                new BufferedReader(new InputStreamReader(executeProcess.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = outputReader.readLine()) != null) {
            output.append(line).append("\n");
        }

        executeProcess.waitFor();
        return output.toString();
    }
}
