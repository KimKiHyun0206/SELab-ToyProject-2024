package com.example.project.comment.controller;

import com.example.project.comment.dto.CommentRegisterRequest;
import com.example.project.comment.dto.CommentResponse;
import com.example.project.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public CommentResponse get(@PathVariable Long id) {
        return commentService.get(id);
    }

    @GetMapping("/all/{boardId}")
    public List<CommentResponse> getAll(@PathVariable Long boardId) {
        return commentService.getAll(boardId);
    }

    @PostMapping
    public CommentResponse register(@RequestBody CommentRegisterRequest request){
        return  commentService.register(request);
    }
}
