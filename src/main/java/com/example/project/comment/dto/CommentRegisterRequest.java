package com.example.project.comment.dto;

import com.example.project.comment.domain.Comment;
import com.example.project.restrictions.RegisterRequest;
import lombok.Data;

@Data
public class CommentRegisterRequest implements RegisterRequest<Comment> {
    private Long boardId;
    private Long userId;
    private String comment;

    @Override
    public Comment toEntity() {
        return new Comment(
                boardId,
                userId,
                comment
        );
    }
}
