package com.example.project.board.service;

import com.example.project.board.dto.BoardResponse;
import com.example.project.board.dto.request.*;
import com.example.project.board.domain.Board;
import com.example.project.board.repository.BoardRepository;
import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.board.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponse register(BoardRegisterRequest request) {
        duplicateValidationBoardTitle(request.getTitle());

        Board savedBoard = boardRepository.save(request.toEntity());

        return savedBoard.toResponseDto();
    }

    @Transactional(readOnly = true)
    public void duplicateValidationBoardTitle(String title) {
        boardRepository.findByTitle(title).ifPresent(board -> {
            throw new AlreadyExistBoardNameException();
        });
    }


    @Transactional
    public BoardResponse update(BoardUpdateRequest request) {

        Board board = boardRepository
                .findById(request.getId())
                .orElseThrow(InvalidBoardIdException::new);

        if (board.getId().equals(request.getId())) {
            board.updateBoard(request.getTitle(), request.getContext());
            return board.toResponseDto();
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public BoardResponse read(BoardReadRequest request) {

        Board board = boardRepository
                .findById(request.getBoardId())
                .orElseThrow(InvalidBoardIdException::new);


        return board.toResponseDto();
    }


    @Transactional(readOnly = true)
    public List<BoardResponse> readAll(Pageable pageable) {

        return boardRepository.findAll(pageable)
                .stream()
                .map(Board::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardResponse delete(BoardDeleteRequest request) {
        Board board = boardRepository
                .findById(request.getBoardId())
                .orElseThrow(NotExistBoardException::new);

        if (board.getUserId().equals(request.getUserId())) {
            boardRepository.delete(board);
            return board.toResponseDto();
        } else throw new BoardIdNotMatchException();
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> readAllBySolutionId(Long id) {
        return boardRepository.findBoardsBySolutionId(id).stream()
                .map(Board::toResponseDto)
                .collect(Collectors.toList());
    }
}