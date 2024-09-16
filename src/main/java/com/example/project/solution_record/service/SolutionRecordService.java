package com.example.project.solution_record.service;

import com.example.project.solution_record.dto.SolutionRecordRegisterRequest;
import com.example.project.solution_record.repository.SolutionRecordRepository;
import com.example.project.solution_record.domain.SolutionRecord;
import com.example.project.solution_record.dto.SolutionRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolutionRecordService {
    private final SolutionRecordRepository solutionRecordRepository;

    @Transactional
    public SolutionRecordResponse register(SolutionRecordRegisterRequest request) {
        return solutionRecordRepository
                .save(request.toEntity())
                .toResponseDto();
    }

    @Transactional(readOnly = true)
    public List<SolutionRecordResponse> getByUserId(Long userId) {
        return solutionRecordRepository
                .findByUserId(userId)
                .stream()
                .map(SolutionRecord::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SolutionRecordResponse> getByUserIdAndSolutionId(Long userId, Long solutionId) {
        return solutionRecordRepository
                .findByUserIdAndSolutionId(userId, solutionId)
                .stream()
                .map(SolutionRecord::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SolutionRecordResponse> findSolutionRecord() {
        return solutionRecordRepository.findSolutionRecord();
    }
}