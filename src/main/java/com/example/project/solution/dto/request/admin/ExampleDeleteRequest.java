package com.example.project.solution.dto.request.admin;

import lombok.Data;

@Data
public class ExampleDeleteRequest {
    private Long solutionId;
    private Long exampleId;
}