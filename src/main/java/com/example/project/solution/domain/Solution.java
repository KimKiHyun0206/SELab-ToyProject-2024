package com.example.project.solution.domain;

import com.example.project.common.BaseEntity;
import com.example.project.restrictions.Domain;
import com.example.project.solution.domain.vo.Difficulty;
import com.example.project.solution.dto.response.list.AuthSolutionListResponse;
import com.example.project.solution.dto.response.SolutionResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "solution", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Example> examples = new ArrayList<>();

    private Long solved;

    public Solution(Difficulty difficulty, String title, String description, Long solved) {
        this.difficulty = difficulty;
        this.title = title;
        this.description = description;
        this.solved = solved;
    }

    public void update(Difficulty difficulty, String title, String description) {
        this.difficulty = difficulty;
        this.title = title;
        this.description = description;
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

    public void addExample(Example example) {
        examples.add(example);
    }

    public void removeExample(Example example) {
        examples.remove(example);
    }
}
