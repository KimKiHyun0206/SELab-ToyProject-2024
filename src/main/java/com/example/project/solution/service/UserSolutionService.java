package com.example.project.solution.service;

import com.example.project.solution.dto.response.list.NonAuthSolutionListResponse;
import com.example.project.solution.dto.response.list.AuthSolutionListResponse;
import com.example.project.solution.dto.response.SolutionResponse;
import com.example.project.solution.domain.Solution;
import com.example.project.error.exception.solution.SolutionException;
import com.example.project.solution.repository.SolutionRecordRepository;
import com.example.project.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSolutionService {

    private final SolutionRepository solutionRepository;
    private final SolutionRecordRepository recordRepository;

    @Transactional(readOnly = true)
    public List<SolutionResponse> readAll(Pageable pageable) {
        return solutionRepository
                .findAll(pageable)
                .stream()
                .map(Solution::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SolutionResponse read(Long id) {
        Solution solution = solutionRepository.findById(id).orElseThrow(SolutionException::new);

        return solution.toResponseDto();
    }

    @Transactional(readOnly = true)
    public List<AuthSolutionListResponse> getAuthSolutionList(Long userId){
        log.info("eachUserSolutionListRead -> {}", userId);
        return recordRepository.eachUserSolutionResponse(userId);
    }


    @Transactional(readOnly = true)
    public List<NonAuthSolutionListResponse> getNonAuthSolutionList(){
        log.info("getNonAuthSolutionList entry");
        return solutionRepository.getSolutions();
    }
}
