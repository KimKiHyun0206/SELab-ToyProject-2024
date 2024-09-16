package com.example.project.compile.service;

import com.example.project.compile.domain.Language;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private static final String CODE_DIR = "/home/ubuntu/code/";

    public Path createCodeFile(String code, Language language) throws IOException {
        Path codePath = Paths.get(CODE_DIR);
        if (!Files.exists(codePath)) {
            Files.createDirectories(codePath);
        }

        String filename = generateFileName(language);
        Path filePath = codePath.resolve(filename);
        Files.write(filePath, code.getBytes());
        return filePath;
    }

    public void deleteCodeFile(Path filePath) throws IOException {
        Files.deleteIfExists(filePath);
    }

    private String generateFileName(Language language) {
        return UUID.randomUUID() + language.getExtension();
    }
}
