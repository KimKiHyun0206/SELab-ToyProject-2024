package com.example.project.solution.dto.response.list;

import com.example.project.solution.domain.vo.Difficulty;
import lombok.Builder;
import lombok.Data;

@Data
public class AuthSolutionListResponse {
    private Difficulty difficulty;
    private String title;
    private Long solved;
    private Boolean successOrNot;

    @Builder
    public AuthSolutionListResponse(Difficulty difficulty, String title, Long solved, Boolean successOrNot) {
        this.difficulty = difficulty;
        this.title = title;
        this.solved = solved;
        this.successOrNot = successOrNot;
    }
}
