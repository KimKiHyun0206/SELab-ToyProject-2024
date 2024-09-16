package com.example.project.compile.service;

import com.example.project.compile.domain.Language;
import com.example.project.error.dto.ErrorMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CompileService {

    private final FileService fileService;
    private final CommandExecutorService commandExecutorService;

    public CompileService(FileService fileService, CommandExecutorService commandExecutorService) {
        this.fileService = fileService;
        this.commandExecutorService = commandExecutorService;
    }

    public String compileAndRun(String languageStr, String code) throws IOException, InterruptedException {
        Language language = Language.fromString(languageStr);
        Path filePath = null;
        try {
            filePath = fileService.createCodeFile(code, language);
            return executeCode(language, filePath);
        } finally {
            if (filePath != null) {
                fileService.deleteCodeFile(filePath);
            }
        }
    }

    private String executeCode(Language language, Path filePath) throws IOException, InterruptedException {
        String result;
        try {
            String command = String.format(language.getCompileCommand(), filePath.toString(), "output");
            result = commandExecutorService.runCommand(command);

            if (language == Language.C || language == Language.CPP) {
                String outputFileName = filePath.getParent().resolve("output").toString();
                result = commandExecutorService.runCommand(outputFileName);
            } else if (language == Language.JAVA) {
                String className = extractClassName(filePath);
                command = String.format("java -Dfile.encoding=UTF-8 -cp %s %s", filePath.getParent(), className);
                result = commandExecutorService.runCommand(command);
            }
        } catch (IOException | InterruptedException e) {
            throw new IOException(ErrorMessage.GENERAL_COMPILE_ERROR.getMessage(), e);
        }
        return result;
    }


    private String extractClassName(Path filePath) throws IOException {
        String content = Files.readString(filePath);
        return content.split("public class ")[1].split("\\s")[0].trim();
    }
}
