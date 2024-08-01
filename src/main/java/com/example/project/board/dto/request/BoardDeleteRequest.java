package com.example.project.board.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoardDeleteRequest {

    @NotNull
    private String userId;
    @NotNull
    private Long boardId;
}
