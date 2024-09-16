package com.example.project.solution.dto.response.list;

import com.example.project.solution.domain.vo.Difficulty;
import lombok.Builder;
import lombok.Data;

@Data
public class NonAuthSolutionListResponse {
    private Difficulty difficulty;
    private String title;
    private Long solved;

    @Builder
    public NonAuthSolutionListResponse(Difficulty difficulty, String title, Long solved) {
        this.difficulty = difficulty;
        this.title = title;
        this.solved = solved;
    }
}