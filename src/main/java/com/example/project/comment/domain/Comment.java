package com.example.project.comment.domain;

import com.example.project.comment.dto.CommentResponse;
import com.example.project.common.BaseEntity;
import com.example.project.restrictions.Domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity implements Domain<CommentResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;
    private Long userId;
    private String comment;

    public Comment(Long boardId, Long userId, String comment) {
        this.boardId = boardId;
        this.userId = userId;
        this.comment = comment;
    }


    @Override
    public CommentResponse toResponseDto() {
        return new CommentResponse(
                id,
                boardId,
                userId,
                comment
        );
    }
}
