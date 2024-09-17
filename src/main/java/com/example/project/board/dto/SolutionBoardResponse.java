package com.example.project.board.dto;

import com.example.project.comment.domain.Comment;
import com.example.project.user.domain.vo.Name;
import lombok.Data;

import java.util.List;


@Data
public class SolutionBoardResponse {
    private String title;
    private String context;
    private String userName;
    private Long solutionId;
    private List<Comment> comments;

    public SolutionBoardResponse(String title, String context, Name userName, Long solutionId) {
        this.title = title;
        this.context = context;
        this.userName = userName.getName();
        this.solutionId = solutionId;
    }

    public void addComments(List<Comment> comments){
        this.comments.addAll(comments);
    }
}
