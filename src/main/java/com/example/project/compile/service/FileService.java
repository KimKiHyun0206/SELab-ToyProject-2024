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

    private final String codeDir;   //한 번 쓰는 변수인데 이걸 필드값으로 가질 이유가 있나요?

    public FileService(@Value("${compile.url}") String codeDir) {
        this.codeDir = codeDir;
    }

    public Path createCodeFile(String code, CompileLanguage compileLanguage) throws IOException {
        Path codePath = Paths.get(codeDir);
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

    //원래는 하나였던 메소드를 위의 메소드에서 더 나눈 이유가 뭔가요?
    private String extractClassName(String code) {
        return code.split("public class ")[1].split("\\s")[0].trim();
    }

    //이 한 줄만 처리한다면 메소드로 나눌 이유가 있을까요?
    public void deleteCodeFile(Path filePath) throws IOException {
        Files.deleteIfExists(filePath);
    }

    private String generateFileName(CompileLanguage language) {
        //UUID를 받아오는 이유가 뭔가요
        return UUID.randomUUID() + language.getExtension();
    }
}
