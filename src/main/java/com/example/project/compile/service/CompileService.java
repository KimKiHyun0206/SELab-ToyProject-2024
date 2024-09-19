package com.example.project.compile.service;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.compile.domain.CompileLanguage;
import com.example.project.error.dto.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompileService {

    private final FileService fileService;
    private final CommandExecutorService commandExecutorService;


    public String compileAndRun(String languageStr, String code) throws IOException, InterruptedException {
        CompileLanguage language = CompileLanguage.getByLanguageName(languageStr);
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

    private String executeCode(CompileLanguage compileLanguage, Path filePath) throws IOException {
        String result;
        try {
            String command = String.format(compileLanguage.getCompileCommand(), filePath.toString(), "output");
            result = commandExecutorService.runCommand(command);

            if (compileLanguage == CompileLanguage.C || compileLanguage == CompileLanguage.CPP) {
                String outputFileName = filePath.getParent().resolve("output").toString();
                result = commandExecutorService.runCommand(outputFileName);
            } else if (compileLanguage == CompileLanguage.JAVA) {
                String className = fileService.extractClassNameFromFile(filePath);
                command = String.format("java -Dfile.encoding=UTF-8 -cp %s %s", filePath.getParent(), className);
                result = commandExecutorService.runCommand(command);
            }
        } catch (IOException | InterruptedException e) {
            throw new IOException(ErrorMessage.GENERAL_COMPILE_ERROR.getMessage(), e);
        }
        return result;
    }
}
