package com.example.project.solution.dto.request.user;

import lombok.Data;

@Data
public class SolutionCompileRequest {
    private String userId;
    private Long solutionId;
    private String code;
    private String language;
}
