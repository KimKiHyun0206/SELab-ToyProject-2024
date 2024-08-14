package com.example.project.compile.dto;

import lombok.Data;

@Data
public class CompileRequest {
    private String language;
    private String code;
}