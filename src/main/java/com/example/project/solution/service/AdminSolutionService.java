package com.example.project.solution.service;

import com.example.project.solution.dto.request.admin.ExampleDeleteRequest;
import com.example.project.solution.dto.request.admin.ExampleRegisterRequest;
import com.example.project.error.exception.example.ExampleNotFindByIdException;
import com.example.project.solution.domain.Example;
import com.example.project.solution.domain.vo.Difficulty;
import com.example.project.solution.domain.vo.VariableType;
import com.example.project.solution.dto.request.admin.*;
import com.example.project.solution.dto.response.SolutionResponse;
import com.example.project.solution.domain.Solution;
import com.example.project.error.exception.solution.SolutionException;
import com.example.project.solution.repository.ExampleRepository;
import com.example.project.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AdminSolutionService {
    private final SolutionRepository solutionRepository;
    private final ExampleRepository exampleRepository;

    @Transactional
    public SolutionResponse register(SolutionRegisterRequest request) {
        return solutionRepository
                .save(request.toEntity())
                .toResponseDto();
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
                request.getDescription()
        );
        return solution.toResponseDto();
    }

    @Transactional
    public void createSolutionWithExample(String title, String description, Difficulty difficulty, String inExample, String outExample) {
        Solution solution = new Solution(difficulty, title, description, 0L);
        solutionRepository.save(solution);

        Example example = new Example(inExample, outExample, solution);

        solution.addExample(example);

        exampleRepository.save(example);
    }

    @Transactional
    public void addExampleToSolution(ExampleRegisterRequest request) {
        Solution solution = solutionRepository.findById(request.getSolutionId())
                .orElseThrow(SolutionException::new);

        Example example = request.toEntity(solution);
        exampleRepository.save(example);
    }

    @Transactional
    public void removeExampleFromSolution(ExampleDeleteRequest request) {
        Long solutionId = request.getSolutionId();
        Long exampleId = request.getExampleId();

        Solution solution = solutionRepository.findById(solutionId)
                .orElseThrow(SolutionException::new);

        Example example = exampleRepository.findById(exampleId)
                .orElseThrow(ExampleNotFindByIdException::new);

        solution.removeExample(example);
        exampleRepository.delete(example);
    }

    public String inExampleConvert(String inExample) {
        VariableType type = VariableType.determineType(inExample);
        return type.convert(inExample).toString();
    }

    public String outExampleConvert(String outExample) {
        VariableType type = VariableType.determineType(outExample);
        return type.convert(outExample).toString();
    }
}