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
            throw new AlreadyExistBoardNameException(ErrorMessage.DUPLICATE_BOARD_NAME_DUPLICATE, "중복된 이름을 가진 Board가 있습니다");
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
                .orElseThrow(() -> new InvalidBoardIdException(ErrorMessage.BOARD_NOT_FOUND_ERROR, "Board 가 존재하지 않습니다"));


        return board.toResponseDto();
    }


    @Transactional(readOnly = true)
    public List<BoardResponse> readAll(Pageable pageable) {

        return boardRepository.findAll(pageable)
                .stream()
                .map(Board::toResponseDto)
                .collect(Collectors.toList());
    }

    /*
    @Transactional(readOnly = true)
    public Page<BoardResponse> readSolutionBoard(Pageable pageable, BoardReadAllRequest request){
        return repository
                .findBySolutionId(request.getSolutionId())
                .map(BoardResponse::from);
    }*/

    @Transactional
    public BoardResponse delete(BoardDeleteRequest request) {
        Board board = boardRepository
                .findById(request.getBoardId())
                .orElseThrow(() -> new NotExistBoardException(ErrorMessage.BOARD_NOT_FOUND_ERROR, "Board 가 존재하지 않습니다"));

        if (board.getUserId().equals(request.getUserId())) {
            boardRepository.delete(board);
            return board.toResponseDto();
        }else throw new BoardIdNotMatchException(ErrorMessage.ID_NOT_MATCH_TO_DELETE_BOARD,"작성자 ID와 요청자 ID가 달라서 삭제할 수 없습니다");
    }
}