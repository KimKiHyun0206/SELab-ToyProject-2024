package com.example.project.compile.service;

import com.example.project.error.dto.ErrorMessage;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.UUID;

@Service
public class CompileService {

    private static final String CODE_DIR = "/home/ubuntu/code/";

    public String compileAndRun(String language, String code) throws IOException, InterruptedException {
        Path filePath = null;
        try {
            Path codePath = Paths.get(CODE_DIR);
            if (!Files.exists(codePath)) {
                Files.createDirectories(codePath);
            }

            String filename = generateFileName(language);
            filePath = codePath.resolve(filename);

            Files.write(filePath, code.getBytes());

            return executeCode(language, filePath, codePath);
        } catch (FileAlreadyExistsException e) {
            throw new IOException(ErrorMessage.CODE_DIRECTORY_CREATION_FAILED.getMessage(), e);
        } catch (IOException e) {
            throw new IOException(ErrorMessage.CODE_WRITE_FAILED.getMessage(), e);
        } finally {
            if (filePath != null) {
                Files.deleteIfExists(filePath);
            }
        }
    }

    private String executeCode(String language, Path filePath, Path codePath) throws IOException {
        String result;
        try {
            result = switch (language.toLowerCase()) {
                case "c" -> compileAndRun(filePath, "gcc", codePath.resolve("output").toString());
                case "cpp", "c++" -> compileAndRun(filePath, "g++", codePath.resolve("output").toString());
                case "java" -> compileJava(filePath);
                case "python" -> compilePython(filePath);
                case "javascript", "js" -> runJavaScript(filePath);
                default -> throw new IllegalArgumentException(ErrorMessage.UNSUPPORTED_LANGUAGE.getMessage());
            };
        } catch (IOException | InterruptedException e) {
            throw new IOException(ErrorMessage.GENERAL_COMPILE_ERROR.getMessage(), e);
        }
        return result;
    }

    private String compileAndRun(Path filePath, String compiler, String outputFileName ) throws IOException, InterruptedException {
        String command = String.format("%s %s -o %s", compiler, filePath, outputFileName);
        runCommand(command);
        return runCompiledProgram(outputFileName);
    }

    private String runCompiledProgram(String outputFileName) throws IOException, InterruptedException {
        return runCommand(outputFileName);
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
        runCommand(command);
        return runJavaProgram(correctFilePath);
    }

    private String runJavaProgram(Path filePath) throws IOException, InterruptedException {
        String className = filePath.getFileName().toString().replace(".java", "");
        String runCommand = String.format("java -cp %s %s", filePath.getParent(), className);
        return runCommand(runCommand);
    }

    private String compilePython(Path filePath) throws IOException, InterruptedException {
        String command = String.format("python %s", filePath);
        return runCommand(command);
    }

    private String runJavaScript(Path filePath) throws IOException, InterruptedException {
        String command = String.format("node %s", filePath);
        return runCommand(command);
    }

    private String runCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

        if (process.exitValue() == 0) {
            return readStream(process.getInputStream());
        } else {
            String errorOutput = readStream(process.getErrorStream());
            throw new IOException(ErrorMessage.EXECUTION_FAILED.getMessage() + "\n" + errorOutput);
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

    private String generateFileName(String language) {
        String extension = getExtension(language);
        return UUID.randomUUID() + extension;
    }

    private String getExtension(String language) {
        return switch (language.toLowerCase()) {
            case "c" -> ".c";
            case "cpp", "c++" -> ".cpp";
            case "java" -> ".java";
            case "python" -> ".py";
            case "javascript", "js" -> ".js";
            default -> throw new IllegalArgumentException(ErrorMessage.UNSUPPORTED_LANGUAGE.getMessage());
        };
    }

    private String readStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }
        return output.toString();
    }
}
