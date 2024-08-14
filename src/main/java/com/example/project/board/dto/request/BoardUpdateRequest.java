package com.example.project.board.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoardUpdateRequest {
    @NotNull
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private String title;
    @NotNull
    private String context;
}
