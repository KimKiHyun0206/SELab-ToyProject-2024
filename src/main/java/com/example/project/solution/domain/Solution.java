package com.example.project.solution.domain;

import com.example.project.common.BaseEntity;
import com.example.project.solution.dto.SolutionResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Solution extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Difficulty difficulty;
    private String title;
    private String description;
    private String inExample;
    private String outExample;
    private Long solved;

    public Solution(Difficulty difficulty, String title, String description, String inExample, String outExample, Long solved) {
        this.difficulty = difficulty;
        this.title = title;
        this.description = description;
        this.inExample = inExample;
        this.outExample = outExample;
        this.solved = solved;
    }

    public void update(Difficulty difficulty, String title, String description, String inExample, String outExample) {
        this.difficulty = difficulty;
        this.title = title;
        this.description = description;
        this.inExample = inExample;
        this.outExample = outExample;
    }

    public void updateDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void updateContext(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void updateExample(String inExample, String outExample) {
        this.inExample = inExample;
        this.outExample = outExample;
    }


    public void increaseSolved() {
        this.solved++;
    }

    public SolutionResponse toResponseDto(){
        return SolutionResponse.builder()
                .id(id)
                .difficulty(difficulty)
                .title(title)
                .description(description)
                .inExample(inExample)
                .outExample(outExample)
                .solved(solved)
                .build();
    }
}
