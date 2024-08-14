package com.example.project.solution.dto.request.admin.update;

import lombok.Data;

@Data
public class SolutionExampleUpdateRequest {
    private String adminId;
    private Long solutionId;
    private String inExample;
    private String outExample;
}
