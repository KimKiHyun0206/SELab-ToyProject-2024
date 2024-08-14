package com.example.project.solution.dto.request.admin;

import lombok.Data;

@Data
public class SolutionDeleteRequest {
    private String adminId;
    private Long solutionId;
}
