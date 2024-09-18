package com.example.project.compile.service;

import com.example.project.compile.domain.CompileLanguage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private final String code_dir;

    public FileService(@Value("${compile.url}") String codeDir) {
        code_dir = codeDir;
    }

    public Path createCodeFile(String code, CompileLanguage compileLanguage) throws IOException {
        Path codePath = Paths.get(code_dir);
        if (!Files.exists(codePath)) {
            Files.createDirectories(codePath);
        }

        String filename;
        if (compileLanguage == CompileLanguage.JAVA) {
            filename = extractClassName(code) + compileLanguage.getExtension();
        } else {
            filename = generateFileName(compileLanguage);
        }
        Path filePath = codePath.resolve(filename);
        Files.writeString(filePath, code);
        return filePath;
    }

    public String extractClassNameFromFile(Path filePath) throws IOException {
        String code = Files.readString(filePath);
        return extractClassName(code);
    }

    private String extractClassName(String code) {
        return code.split("public class ")[1].split("\\s")[0].trim();
    }

    public void deleteCodeFile(Path filePath) throws IOException {
        Files.deleteIfExists(filePath);
    }

    private String generateFileName(CompileLanguage language) {
        return UUID.randomUUID() + language.getExtension();
    }
}
