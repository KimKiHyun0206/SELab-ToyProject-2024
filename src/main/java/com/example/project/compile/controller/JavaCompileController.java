package com.example.project.compile.controller;

import com.example.project.compile.service.JavaCompileService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/execute")
@RequiredArgsConstructor
public class JavaCompileController {
    private final JavaCompileService executionService;

    @PostMapping
    public String executeJavaCode(@RequestBody String javaCode) {
        return executionService.executeJavaCode(javaCode);
    }
}