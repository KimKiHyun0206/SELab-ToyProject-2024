package com.example.project.board.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoardReadRequest {
    @NotNull
    private Long boardId;
}
