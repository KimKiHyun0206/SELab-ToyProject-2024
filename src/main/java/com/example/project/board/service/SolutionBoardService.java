package com.example.project.board.service;

import com.example.project.board.dto.SolutionBoardResponse;
import com.example.project.board.repository.BoardRepository;
import com.example.project.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolutionBoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public SolutionBoardResponse readSolutionBoard(Long boardId){
        log.info("entry readSolutionBoard -> {}", boardId);
        SolutionBoardResponse solutionBoardResponses = boardRepository.readBoards(boardId);
        solutionBoardResponses.addComments(commentRepository.findByBoardIdOrderByCreatedAt(boardId));

        return solutionBoardResponses;
    }
}
