package com.example.project.solution.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public enum VariableType {
    INTEGER("-?\\d+", Integer::parseInt),
    DOUBLE("-?\\d*\\.\\d+", Double::parseDouble),
    BOOLEAN("true|false", Boolean::parseBoolean, Pattern.CASE_INSENSITIVE),
    CHAR("^.$", value -> {
        if (value.length() == 1) {
            return value.charAt(0);
        } else {
            return null; // 예외 처리 예정
        }
    }),
    STRING(".*", value -> value);

    private final Pattern pattern;
    private final Converter converter;

    VariableType(String regex, Converter converter) {
        this.pattern = Pattern.compile(regex);
        this.converter = converter;
    }

    VariableType(String regex, Converter converter, int flags) {
        this.pattern = Pattern.compile(regex, flags);
        this.converter = converter;
    }

    public boolean matches(String value) {
        return pattern.matcher(value).matches();
    }

    public Object convert(String value) {
        return converter.convert(value);
    }

    public static VariableType determineType(String value) {
        for (VariableType type : values()) {
            if (type.matches(value)) {
                return type;
            }
        }
        return STRING;
    }

    @FunctionalInterface
    private interface Converter {
        Object convert(String value);
    }
}