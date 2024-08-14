package com.example.project.solution.dto.request.admin.update;

import com.example.project.solution.domain.vo.Difficulty;
import lombok.Data;

@Data
public class SolutionUpdateRequest {
    private String adminId;
    private Long solutionId;
    private Difficulty difficulty;
    private String title;
    private String description;
    private String inExample;
    private String outExample;
}
