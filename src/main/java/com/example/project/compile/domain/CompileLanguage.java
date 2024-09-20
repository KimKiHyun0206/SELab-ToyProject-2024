package com.example.project.compile.domain;

import com.example.project.error.dto.ErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompileLanguage {
    C(".c", "gcc %s -o %s", new String[]{"c"}),
    CPP(".cpp", "g++ %s -o %s", new String[]{"cpp"}),
    JAVA(".java", "javac -encoding UTF-8 %s", new String[]{"java"}),
    PYTHON(".py", "python -X utf8 %s", new String[]{"python"}),
    JAVASCRIPT(".js", "node %s", new String[]{"js"});

    private final String extension;
    private final String compileCommand;
    private final String[] languageNames;

    public static CompileLanguage getByLanguageName(String compileLanguage) {
        for (CompileLanguage language : CompileLanguage.values()) {
            for (String name : language.languageNames) {
                if (name.equalsIgnoreCase(compileLanguage)) {
                    return language;
                }
            }
        }
        throw new IllegalArgumentException(ErrorMessage.UNSUPPORTED_LANGUAGE.getMessage());
    }
}