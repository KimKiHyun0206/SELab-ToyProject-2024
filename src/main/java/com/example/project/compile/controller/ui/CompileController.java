package com.example.project.compile.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compile")
public class CompileController {
    @RequestMapping
    public String CompilePage() {
        return "compile/compile";
    }
}