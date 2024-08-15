package com.example.project.compile.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compile")
public class CompileController {
    @GetMapping
    public String CompilePage() {
        return "compile";
    }
}
