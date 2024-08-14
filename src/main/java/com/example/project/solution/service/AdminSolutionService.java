package com.example.project.solution.service;

import com.example.project.solution.dto.SolutionResponse;
import com.example.project.solution.dto.request.admin.SolutionDeleteRequest;
import com.example.project.solution.dto.request.admin.SolutionRegisterRequest;
import com.example.project.solution.dto.request.admin.update.SolutionContextUpdateRequest;
import com.example.project.solution.dto.request.admin.update.SolutionDifficultyUpdateRequest;
import com.example.project.solution.dto.request.admin.update.SolutionExampleUpdateRequest;
import com.example.project.solution.dto.request.admin.update.SolutionUpdateRequest;
import com.example.project.solution.domain.Solution;
import com.example.project.error.exception.solution.SolutionException;
import com.example.project.solution.repository.SolutionRepository;
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
        if (request.getAdminId().equals("admin")) {
            if (solutionRepository.existsById(request.getSolutionId())) {
                Solution solution = solutionRepository.findById(request.getSolutionId()).orElseThrow(SolutionException::new);
                solutionRepository.deleteById(request.getSolutionId());
                return solution.toResponseDto();
            }
        }
        return null;
    }

    private boolean isAdmin(String id) {
        return id.equals("admin");
    }

    @Transactional
    public SolutionResponse contextUpdate(SolutionContextUpdateRequest request) {
        if (isAdmin(request.getAdminId())) {
            Solution solution = solutionRepository.findById(request.getSolutionId())
                    .orElseThrow(SolutionException::new);

            solution.updateContext(request.getTitle(), request.getDescription());

            return solution.toResponseDto();
        }

        return null;
    }

    @Transactional
    public SolutionResponse difficultyUpdate(SolutionDifficultyUpdateRequest request) {
        if (isAdmin(request.getAdminId())) {
            Solution solution = solutionRepository.findById(request.getSolutionId())
                    .orElseThrow(SolutionException::new);

            solution.updateDifficulty(request.getDifficulty());

            return solution.toResponseDto();
        }
        return null;
    }

    @Transactional
    public SolutionResponse exampleUpdate(SolutionExampleUpdateRequest request) {
        if (isAdmin(request.getAdminId())) {
            Solution solution = solutionRepository.findById(request.getSolutionId())
                    .orElseThrow(SolutionException::new);

            solution.updateExample(request.getInExample(), request.getOutExample());

            return solution.toResponseDto();
        }
        return null;
    }

    @Transactional
    public SolutionResponse updateAll(SolutionUpdateRequest request) {
        if (isAdmin(request.getAdminId())) {
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
        return null;
    }
}
