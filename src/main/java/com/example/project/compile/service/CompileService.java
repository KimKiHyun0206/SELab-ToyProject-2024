package com.example.project.compile.service;

import com.example.project.compile.domain.CompileLanguage;
import com.example.project.error.dto.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompileService {

    private final FileService fileService;
    private final CommandExecutorService commandExecutorService;

    public String compileAndRun(String language, String code, String codeDir) throws IOException {
        CompileLanguage compileLanguage = CompileLanguage.JAVA.getByLanguageName(language);
        Path filePath = null;
        try {
            filePath = fileService.createCodeFile(code, compileLanguage, codeDir);
            return executeCode(filePath);
        } finally {
            if (filePath != null) {
                Files.deleteIfExists(filePath);
            }
        }
    }

    private String executeCode(Path filePath) throws IOException {
        String result;
        try {
            String command = filePath.getParent().resolve("output").toString();
            result = commandExecutorService.runCommand(command);
        } catch (IOException | InterruptedException e) {
            throw new IOException(ErrorMessage.GENERAL_COMPILE_ERROR.getMessage(), e);
        }
        return result;
    }
}