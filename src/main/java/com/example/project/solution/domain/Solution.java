package com.example.project.solution.domain;

import com.example.project.common.BaseEntity;
import com.example.project.restrictions.Domain;
import com.example.project.solution.domain.vo.Difficulty;
import com.example.project.solution.dto.response.list.AuthSolutionListResponse;
import com.example.project.solution.dto.response.SolutionResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Solution extends BaseEntity implements Domain<SolutionResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "solution_id", nullable = false)
    private Long id;
    @Enumerated
    private Difficulty difficulty;
    private String title;
    private String description;

    @Convert(converter = StringListConverter.class)
    private String inExample;
    @Convert(converter = StringListConverter.class)
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

    public void increaseSolved() {
        this.solved++;
    }

    @Override
    public SolutionResponse toResponseDto() {
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

    public AuthSolutionListResponse toListResponseDto() {
        return AuthSolutionListResponse.builder()
                .difficulty(difficulty)
                .title(title)
                .solved(solved)
                .build();
    }
}