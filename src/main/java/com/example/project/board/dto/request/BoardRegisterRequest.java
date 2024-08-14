package com.example.project.board.dto.request;

import com.example.project.board.domain.Board;
import com.example.project.restrictions.RegisterRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoardRegisterRequest implements RegisterRequest<Board> {
    @NotNull
    private String title;
    @NotNull
    private String context;
    @NotNull
    private Long userId;
    @NotNull
    private Long solutionId;

    @Override
    public Board toEntity(){
        return Board.builder()
                .title(title)
                .userId(userId)
                .context(context)
                .solutionId(solutionId)
                .build();
    }
}
