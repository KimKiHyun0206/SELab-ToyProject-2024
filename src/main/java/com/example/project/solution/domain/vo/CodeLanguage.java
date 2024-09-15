package com.example.project.solution.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodeLanguage {

    JAVA("Java"), CPP("C++"), PYTHON("Python"), C("C"), JS("JavaScript");

    private final String language;
}
