package com.example.project.solution.controller.rest;


import com.example.project.solution.dto.response.SolutionRecordResponse;
import com.example.project.solution.service.SolutionRecordService;
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
    public ResponseEntity<List<SolutionRecordResponse>> findSolutionRecord(@RequestParam("id") Long id, @RequestParam("solutionId") Long solutionId) {
        List<SolutionRecordResponse> response = solutionRecordService.findSolutionRecord(id, solutionId);
        return ResponseEntity.ok(response);
    }

}
