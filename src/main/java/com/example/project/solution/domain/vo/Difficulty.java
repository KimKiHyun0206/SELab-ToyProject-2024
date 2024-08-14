package com.example.project.solution.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Difficulty {
    EASY(1), MEDIUM(2), HARD(3);

    private final Integer level;
}
