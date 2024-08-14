package com.example.project.solution.service;

import com.example.project.solution.dto.SolutionResponse;
import com.example.project.solution.dto.request.user.SolutionFindRequest;
import com.example.project.solution.domain.Solution;
import com.example.project.error.exception.solution.SolutionException;
import com.example.project.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSolutionService {

    private final SolutionRepository solutionRepository;

    @Transactional(readOnly = true)
    public List<SolutionResponse> readAll(Pageable pageable) {
        return solutionRepository
                .findAll(pageable).stream()
                .map(Solution::toResponseDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SolutionResponse read(SolutionFindRequest request) {
        Solution solution = solutionRepository.findById(request.getSolutionId()).orElseThrow(SolutionException::new);

        return solution.toResponseDto();
    }

   /* @Transactional(readOnly = true)
    public Page<SolutionResponse> findByDifficulty(DifficultyFindRequest request){
        return solutionRepository
                .findByDifficulty(request.getDifficulty())
                .map(SolutionResponse::from);
    }*/

    //TODO 컴파일 부분 만들기
}
