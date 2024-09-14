package com.example.project.solution.dto.response.list;

import com.example.project.solution.domain.vo.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class SolutionListResponse {
    private Difficulty difficulty;
    private String title;
    private Long solved;
    private Boolean successOrNot;

    @Builder
    public SolutionListResponse(Difficulty difficulty, String title, Long solved,Boolean successOrNot) {
        this.difficulty = difficulty;
        this.title = title;
        this.solved = solved;
        this.successOrNot = successOrNot;
    }
}
