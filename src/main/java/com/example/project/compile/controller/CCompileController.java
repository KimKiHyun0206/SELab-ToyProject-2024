package com.example.project.compile.controller;

import com.example.project.compile.service.CCompileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/ccode")
@RequiredArgsConstructor
public class CCompileController {

    private final CCompileService cCompileService;

    @PostMapping("/execute")
    public ResponseEntity<String> executeCCode(@RequestBody String cCode) {
        try {
            String output = cCompileService.executeCCode(cCode);
            return ResponseEntity.ok(output);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error executing C code: " + e.getMessage());
        }
    }
}
