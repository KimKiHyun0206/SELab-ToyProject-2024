package com.example.project.solution.dto.request.admin;

import lombok.Data;

@Data
public class SolutionDeleteRequest {
    private Long adminId;
    private Long solutionId;
}
