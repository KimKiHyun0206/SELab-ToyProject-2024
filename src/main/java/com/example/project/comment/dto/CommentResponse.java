package com.example.project.comment.dto;

import com.example.project.comment.domain.Comment;
import com.example.project.restrictions.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse implements ResponseDto<Comment> {

    private Long id;
    private Long boardId;
    private Long userId;
    private String comment;

    @Override
    public Comment toEntity() {
        return new Comment(
                id,
                boardId,
                userId,
                comment
        );
    }
}
