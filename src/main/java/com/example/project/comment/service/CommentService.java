package com.example.project.comment.service;

import com.example.project.board.dto.BoardResponse;
import com.example.project.comment.domain.Comment;
import com.example.project.comment.dto.CommentDeleteRequest;
import com.example.project.comment.dto.CommentRegisterRequest;
import com.example.project.comment.dto.CommentResponse;
import com.example.project.comment.repository.CommentRepository;
import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.comment.CommentIdNotMatchException;
import com.example.project.error.exception.comment.InvalidCommentException;
import com.example.project.error.exception.user.InvalidLoginInfoException;
import com.example.project.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return commentRepository.findById(id).orElseThrow(
                () -> new InvalidCommentException(ErrorMessage.NOT_EXIST_COMMENT_INFO_EXCEPTION, "요청한 Comment를 찾지 못했습니다")
        ).toResponseDto();
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
                .orElseThrow(
                        () -> new InvalidCommentException(ErrorMessage.NOT_EXIST_COMMENT_INFO_EXCEPTION, "요청한 Comment를 찾지 못했습니다")
                );

        if (comment.getUserId().equals(request.getUserId())) {
            commentRepository.delete(comment);
        } else
            throw new CommentIdNotMatchException(ErrorMessage.ID_NOT_MATCH_TO_DELETE_COMMENT, "요청자와 작성자의 Id가 일치하지 않습니다");
    }
}
