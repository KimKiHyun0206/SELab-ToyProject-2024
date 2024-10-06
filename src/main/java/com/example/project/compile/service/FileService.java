package com.example.project.compile.service;

import com.example.project.compile.domain.CompileLanguage;
import com.example.project.error.dto.ErrorMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    public Path createCodeFile(String code, CompileLanguage compileLanguage, String codeDir) throws IOException {
        try {
            Path codePath = Paths.get(codeDir);
            if (!Files.exists(codePath)) {
                Files.createDirectories(codePath);
            }

            //UUID를 사용하는 이유는  고유한 파일 이름을 생성하여 파일의 충돌을 방지하기 위함입니다.
            String filename = "Main_" + UUID.randomUUID() + compileLanguage.getExtension();

            Path filePath = codePath.resolve(filename);
            Files.writeString(filePath, code);
            return filePath;

        } catch (InvalidPathException e) {
            throw new IOException(ErrorMessage.INVALID_PATH_EXCEPTION.getMessage(), e);
        } catch (SecurityException e) {
            throw new IOException(ErrorMessage.ACCESS_PERMISSION_EXCEPTION.getMessage(), e);
        }
    }
}