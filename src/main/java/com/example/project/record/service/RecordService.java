package com.example.project.record.service;

import com.example.project.record.dto.RecordRegisterRequest;
import com.example.project.record.repository.RecordRepository;
import com.example.project.record.domain.Record;
import com.example.project.record.dto.RecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

    @Transactional
    public RecordResponse register(RecordRegisterRequest request) {
        return recordRepository
                .save(request.toEntity())
                .toResponseDto();
    }

    @Transactional(readOnly = true)
    public List<RecordResponse> getByUserId(Long userId) {
        return recordRepository
                .findByUserId(userId)
                .stream()
                .map(Record::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecordResponse> getByUserIdAndSolutionId(Long userId, Long solutionId) {
        return recordRepository
                .findByUserIdAndSolutionId(userId, solutionId)
                .stream()
                .map(Record::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecordResponse> findSolutionRecord() {
        return recordRepository.findSolutionRecord();
    }
}