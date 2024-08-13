package com.example.project.solution.dto.response;

import com.example.project.solution.domain.vo.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class SolutionListResponse {
    private Long id;
    private Difficulty difficulty;
    private String title;
    private Long solved;

    @Builder
    public SolutionListResponse(Long id, Difficulty difficulty, String title, Long solved) {
        this.id = id;
        this.difficulty = difficulty;
        this.title = title;
        this.solved = solved;
    }
}
