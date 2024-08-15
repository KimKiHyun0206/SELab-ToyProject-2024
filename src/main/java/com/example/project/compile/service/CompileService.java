package com.example.project.compile.service;

import com.example.project.error.dto.ErrorMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class CompileService {

    private static final String CODE_DIR = "/home/ubuntu/code/";

    public String compileAndRun(String language, String code) throws IOException, InterruptedException {
        try {
            Path codePath = Paths.get(CODE_DIR);
            if (!Files.exists(codePath)) {
                Files.createDirectories(codePath);
            }

            String filename = generateFileName(language);
            Path filePath = codePath.resolve(filename);

            Files.write(filePath, code.getBytes());

            return executeCode(language, filePath, codePath);
        } catch (FileAlreadyExistsException e) {
            throw new IOException(ErrorMessage.CODE_DIRECTORY_CREATION_FAILED.getMessage(), e);
        } catch (IOException e) {
            throw new IOException(ErrorMessage.CODE_WRITE_FAILED.getMessage(), e);
        }
    }

    private String executeCode(String language, Path filePath, Path codePath) throws IOException, InterruptedException {
        try {
            return switch (language.toLowerCase()) {
                case "c" -> compileAndRun(filePath, "gcc", codePath.resolve("output").toString(), "");
                case "c++" -> compileAndRun(filePath, "g++", codePath.resolve("output").toString(), "");
                case "java" -> compileJava(filePath);
                case "python" -> compilePython(filePath);
                default -> throw new IllegalArgumentException(ErrorMessage.UNSUPPORTED_LANGUAGE.getMessage());
            };
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessage.UNSUPPORTED_LANGUAGE.getMessage(), e);
        } catch (IOException | InterruptedException e) {
            throw new IOException(ErrorMessage.GENERAL_COMPILE_ERROR.getMessage(), e);
        }
    }

    private String compileAndRun(Path filePath, String compiler, String outputFileName, String additionalArgs) throws IOException, InterruptedException {
        String command = String.format("%s %s -o %s %s", compiler, filePath, outputFileName, additionalArgs);
        Process compileProcess = Runtime.getRuntime().exec(command);
        compileProcess.waitFor();

        if (compileProcess.exitValue() == 0) {
            return runCompiledProgram(outputFileName);
        } else {
            String errorOutput = new String(compileProcess.getErrorStream().readAllBytes());
            throw new IOException(ErrorMessage.COMPILATION_FAILED.getMessage() + "\n" + errorOutput);
        }
    }

    private String runCompiledProgram(String outputFileName) throws IOException, InterruptedException {
        Process runProcess = Runtime.getRuntime().exec(outputFileName);
        runProcess.waitFor();

        if (runProcess.exitValue() == 0) {
            return new String(runProcess.getInputStream().readAllBytes());
        } else {
            String errorOutput = new String(runProcess.getErrorStream().readAllBytes());
            throw new IOException(ErrorMessage.EXECUTION_FAILED.getMessage() + "\n" + errorOutput);
        }
    }

    private String compileJava(Path filePath) throws IOException, InterruptedException {
        String className = extractClassName(filePath);
        Path correctFilePath = filePath.getParent().resolve(className + ".java");

        try {
            Files.move(filePath, correctFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (FileAlreadyExistsException e) {
            throw new IOException(ErrorMessage.JAVA_FILE_RENAMING_FAILED.getMessage(), e);
        }

        String command = String.format("javac %s", correctFilePath);
        Process compileProcess = Runtime.getRuntime().exec(command);
        compileProcess.waitFor();

        if (compileProcess.exitValue() == 0) {
            return runJavaProgram(correctFilePath);
        } else {
            String errorOutput = new String(compileProcess.getErrorStream().readAllBytes());
            throw new IOException(ErrorMessage.COMPILATION_FAILED.getMessage() + "\n" + errorOutput);
        }
    }

    private String extractClassName(Path filePath) throws IOException {
        try {
            String content = Files.readString(filePath);
            return content.split("public class ")[1].split("\\s")[0].trim();
        } catch (Exception e) {
            throw new IOException(ErrorMessage.JAVA_CLASS_EXTRACTION_FAILED.getMessage(), e);
        }
    }

    private String runJavaProgram(Path filePath) throws IOException, InterruptedException {
        String className = filePath.getFileName().toString().replace(".java", "");
        String runCommand = String.format("java -cp %s %s", filePath.getParent(), className);

        Process runProcess = Runtime.getRuntime().exec(runCommand);
        runProcess.waitFor();

        if (runProcess.exitValue() == 0) {
            return new String(runProcess.getInputStream().readAllBytes());
        } else {
            String errorOutput = new String(runProcess.getErrorStream().readAllBytes());
            throw new IOException(ErrorMessage.EXECUTION_FAILED.getMessage() + "\n" + errorOutput);
        }
    }

    private String compilePython(Path filePath) throws IOException, InterruptedException {
        String command = String.format("python %s", filePath);
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

        if (process.exitValue() == 0) {
            return new String(process.getInputStream().readAllBytes());
        } else {
            String errorOutput = new String(process.getErrorStream().readAllBytes());
            throw new IOException(ErrorMessage.EXECUTION_FAILED.getMessage() + "\n" + errorOutput);
        }
    }

    private String generateFileName(String language) {
        String extension = getExtension(language);
        return UUID.randomUUID().toString() + extension;
    }

    private String getExtension(String language) {
        return switch (language.toLowerCase()) {
            case "c" -> ".c";
            case "c++" -> ".cpp";
            case "java" -> ".java";
            case "python" -> ".py";
            default -> throw new IllegalArgumentException(ErrorMessage.UNSUPPORTED_LANGUAGE.getMessage());
        };
    }
}
