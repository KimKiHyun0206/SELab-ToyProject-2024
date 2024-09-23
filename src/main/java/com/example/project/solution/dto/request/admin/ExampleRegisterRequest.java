package com.example.project.solution.dto.request.admin;

import com.example.project.solution.domain.Example;
import com.example.project.solution.domain.Solution;
import lombok.Data;

@Data
public class ExampleRegisterRequest {
    private String inExample;
    private String outExample;
    private Long solutionId;

    public Example toEntity(Solution solution) {
        return new Example(this.inExample, this.outExample, solution);
    }
}
