package com.example.project.comment.dto;

import lombok.Data;

@Data
public class CommentDeleteRequest {
    private Long id;
    private Long userId;
    private String comment;
}
