package com.example.project.solution.service;

import com.example.project.solution.dto.response.SolutionResponse;
import com.example.project.solution.dto.request.admin.SolutionDeleteRequest;
import com.example.project.solution.dto.request.admin.SolutionRegisterRequest;
import com.example.project.solution.dto.request.admin.SolutionUpdateRequest;
import com.example.project.solution.domain.Solution;
import com.example.project.error.exception.solution.SolutionException;
import com.example.project.solution.repository.SolutionRepository;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AdminSolutionService {
    private final SolutionRepository solutionRepository;

    @Transactional
    public SolutionResponse register(SolutionRegisterRequest request) {
        Solution savedSolution = solutionRepository.save(request.toEntity());
        return savedSolution.toResponseDto();
    }

    @Transactional
    public SolutionResponse delete(SolutionDeleteRequest request) {

        if (solutionRepository.existsById(request.getSolutionId())) {
            Solution solution = solutionRepository.findById(request.getSolutionId()).orElseThrow(SolutionException::new);
            solutionRepository.deleteById(request.getSolutionId());
            return solution.toResponseDto();
        }

        return null;
    }

    @Transactional
    public SolutionResponse updateAll(SolutionUpdateRequest request) {

        Solution solution = solutionRepository.findById(request.getSolutionId())
                .orElseThrow(SolutionException::new);

        solution.update(
                request.getDifficulty(),
                request.getTitle(),
                request.getDescription(),
                request.getInExample(),
                request.getOutExample()
        );

        return solution.toResponseDto();
    }
}
