package com.example.project.comment.service;

import com.example.project.comment.domain.Comment;
import com.example.project.comment.dto.CommentDeleteRequest;
import com.example.project.comment.dto.CommentRegisterRequest;
import com.example.project.comment.dto.CommentResponse;
import com.example.project.comment.repository.CommentRepository;
import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.comment.CommentIdNotMatchException;
import com.example.project.error.exception.comment.InvalidCommentException;
import com.example.project.error.exception.comment.NotExistCommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse register(CommentRegisterRequest request) {
        Comment save = commentRepository.save(request.toEntity());

        return save.toResponseDto();
    }

    @Transactional(readOnly = true)
    public CommentResponse get(Long id) {
        return commentRepository
                .findById(id)
                .orElseThrow(InvalidCommentException::new)
                .toResponseDto();
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAll(Long boardId) {
        return commentRepository
                .findByBoardIdOrderByCreatedAt(boardId)
                .stream()
                .map(Comment::toResponseDto)
                .toList();
    }

    @Transactional
    public void delete(CommentDeleteRequest request) {
        Comment comment = commentRepository
                .findById(request.getId())
                .orElseThrow(NotExistCommentException::new);

        if (comment.getUserId().equals(request.getUserId())) {
            commentRepository.delete(comment);
        } else
            throw new CommentIdNotMatchException();
    }
}