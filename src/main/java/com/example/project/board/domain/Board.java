package com.example.project.board.domain;

import com.example.project.board.dto.BoardResponse;
import com.example.project.common.BaseEntity;
import com.example.project.restrictions.Domain;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board extends BaseEntity implements Domain<BoardResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String context;

    @Column
    private Long userId;

    @Column
    private Long solutionId;

    @Builder
    public Board(String title, String context, Long userId, Long solutionId) {
        this.title = title;
        this.context = context;
        this.userId = userId;
        this.solutionId = solutionId;
    }

    public void updateBoard(String title, String context){
        this.title = title;
        this.context = context;
    }

    @Override
    public BoardResponse toResponseDto(){
        return BoardResponse.builder()
                .id(id)
                .title(title)
                .context(context)
                .userId(userId)
                .solutionId(solutionId)
                .build();
    }
}
