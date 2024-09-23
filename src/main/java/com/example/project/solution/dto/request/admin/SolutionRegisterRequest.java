package com.example.project.solution.dto.request.admin;

import com.example.project.restrictions.RegisterRequest;
import com.example.project.solution.domain.Example;
import com.example.project.solution.domain.Solution;
import com.example.project.solution.domain.vo.Difficulty;
import lombok.Data;

@Data
public class SolutionRegisterRequest implements RegisterRequest<Solution> {
    private Difficulty difficulty;
    private String title;
    private String description;
    private String inExample;
    private String outExample;

    @Override
    public Solution toEntity() {
        Solution solution = new Solution(
                this.difficulty,
                this.title,
                this.description,
                0L
        );

        Example example = new Example(this.inExample, this.outExample, solution);
        solution.addExample(example);

        return solution;
    }
}
