package com.example.project.solution.dto.request.admin;

import com.example.project.restrictions.RegisterRequest;
import com.example.project.solution.domain.vo.Difficulty;
import com.example.project.solution.domain.Solution;
import lombok.Data;

@Data
public class SolutionRegisterRequest implements RegisterRequest<Solution> {
    private String adminId;
    private Difficulty difficulty;
    private String title;
    private String description;
    private String inExample;
    private String outExample;

    @Override
    public Solution toEntity() {
        return new Solution(
                this.difficulty,
                this.title,
                this.description,
                this.inExample,
                this.outExample,
                0L
        );
    }
}
