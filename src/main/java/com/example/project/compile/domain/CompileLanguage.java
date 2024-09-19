package com.example.project.compile.domain;

import com.example.project.error.dto.ErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompileLanguage {
    C(".c", "gcc %s -o %s"),
    CPP(".cpp", "g++ %s -o %s"),
    JAVA(".java", "javac -encoding UTF-8 %s"),
    PYTHON(".py", "python -X utf8 %s"),
    JAVASCRIPT(".js", "node %s");

    private final String extension;
    private final String compileCommand;

    //Enum을 사용했으면 Switch문을 사용하지 말아주세요
    public static CompileLanguage getByLanguageName(String language) {
        return switch (language.toLowerCase()) {
            case "c" -> C;
            case "cpp", "c++" -> CPP;
            case "java" -> JAVA;
            case "python" -> PYTHON;
            case "javascript", "js" -> JAVASCRIPT;
            default -> throw new IllegalArgumentException(ErrorMessage.UNSUPPORTED_LANGUAGE.getMessage());
        };
    }
}
