package com.example.project.solution_record.controller;

import com.example.project.solution_record.dto.SolutionRecordResponse;
import com.example.project.solution_record.service.SolutionRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/solutionRecord")
@RequiredArgsConstructor
public class SolutionRecordController {
    private final SolutionRecordService solutionRecordService;

    @GetMapping
    public ResponseEntity<List<SolutionRecordResponse>> findSolutionRecord() {
        List<SolutionRecordResponse> response = solutionRecordService.findSolutionRecord();
        return ResponseEntity.ok(response);
    }
}