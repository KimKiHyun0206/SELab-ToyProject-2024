package com.example.project.solution.dto;

import com.example.project.restrictions.ResponseDto;
import com.example.project.solution.domain.vo.Difficulty;
import com.example.project.solution.domain.Solution;
import lombok.Builder;
import lombok.Data;

@Data
public class SolutionResponse implements ResponseDto<Solution> {
    private Long id;
    private Difficulty difficulty;
    private String title;
    private String description;
    private String inExample;
    private String outExample;
    private Long solved;
    @Builder
    public SolutionResponse(Long id, Difficulty difficulty, String title, String description, String inExample, String outExample, Long solved) {
        this.id = id;
        this.difficulty = difficulty;
        this.title = title;
        this.description = description;
        this.inExample = inExample;
        this.outExample = outExample;
        this.solved = solved;
    }

    @Override
    public Solution toEntity() {
        return new Solution(
                this.difficulty,
                this.title,
                this.description,
                this.inExample,
                this.outExample,
                this.solved
        );
    }
}
