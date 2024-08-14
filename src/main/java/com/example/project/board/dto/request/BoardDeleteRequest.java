package com.example.project.board.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoardDeleteRequest {

    @NotNull
    private  Long userId;
    @NotNull
    private Long boardId;
}
