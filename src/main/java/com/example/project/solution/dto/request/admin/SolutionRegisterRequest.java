package com.example.project.solution.dto.request.admin;

import com.example.project.restrictions.RegisterRequest;
import com.example.project.solution.domain.StringListConverter;
import com.example.project.solution.domain.vo.Difficulty;
import com.example.project.solution.domain.Solution;
import jakarta.persistence.Convert;
import lombok.Data;

import java.util.List;

@Data
public class SolutionRegisterRequest implements RegisterRequest<Solution> {
    private Difficulty difficulty;
    private String title;
    private String description;

    @Convert(converter = StringListConverter.class)
    private String inExample;

    @Convert(converter = StringListConverter.class)
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