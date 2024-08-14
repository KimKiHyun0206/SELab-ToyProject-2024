package com.example.project.solution.dto.request.admin.update;

import com.example.project.solution.domain.vo.Difficulty;
import lombok.Data;

@Data
public class SolutionDifficultyUpdateRequest {
    private String adminId;
    private Long solutionId;
    private Difficulty difficulty;
}
