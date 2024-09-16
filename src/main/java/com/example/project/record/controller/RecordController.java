package com.example.project.record.controller;

import com.example.project.record.dto.RecordResponse;
import com.example.project.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/solutionRecord")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<List<RecordResponse>> findSolutionRecord() {
        List<RecordResponse> response = recordService.findSolutionRecord();
        return ResponseEntity.ok(response);
    }
}