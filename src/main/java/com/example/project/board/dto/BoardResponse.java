package com.example.project.board.dto;

import com.example.project.board.domain.Board;
import com.example.project.restrictions.ResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardResponse implements ResponseDto<Board> {
    private Long id;
    private String title;
    private String context;
    private Long userId;
    private Long solutionId;

    @Builder
    public BoardResponse(Long id, String title, String context, Long userId, Long solutionId) {
        this.id = id;
        this.title = title;
        this.context = context;
        this.userId = userId;
        this.solutionId = solutionId;
    }

    @Override
    public Board toEntity() {
        return new Board(
                this.id,
                this.title,
                this.context,
                this.userId,
                this.solutionId
        );
    }
}