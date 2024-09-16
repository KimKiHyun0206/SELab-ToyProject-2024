package com.example.project.compile.domain;

import com.example.project.error.dto.ErrorMessage;

public enum Language {
    C(".c", "gcc %s -o %s"),
    CPP(".cpp", "g++ %s -o %s"),
    JAVA(".java", "javac -encoding UTF-8 %s"),
    PYTHON(".py", "python -X utf8 %s"),
    JAVASCRIPT(".js", "node %s");

    private final String extension;
    private final String compileCommand;

    Language(String extension, String compileCommand) {
        this.extension = extension;
        this.compileCommand = compileCommand;
    }

    public String getExtension() {
        return extension;
    }

    public String getCompileCommand() {
        return compileCommand;
    }

    public static Language fromString(String language) {
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
