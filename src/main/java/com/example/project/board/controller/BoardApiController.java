package com.example.project.board.controller;

import com.example.project.board.dto.BoardResponse;
import com.example.project.board.dto.request.*;
import com.example.project.board.service.BoardService;
import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/board")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    /**
     * @param request : Board를 등록할 정보를 가진 dto
     * @return BoardResponse : 등록된 Board에 대한 정보를 가진 dto
     * */
    @PostMapping
    public ResponseEntity<?> register(@RequestBody BoardRegisterRequest request) {
        var response = boardService.register(request);

        return ResponseDto.toResponseEntity(ResponseMessage.CREATE_SUCCESS_BOARD, response);
    }


    /**
     * @param request : 읽을 Board에 대한 정보를 가진 dto
     * @return BoardResponse : 읽은 Board에 대한 정보를 가진 dto
     * */
    @GetMapping
    public ResponseEntity<?> read(@RequestBody BoardReadRequest request) {
        var response = boardService.read(request);

        return ResponseDto.toResponseEntity(ResponseMessage.READ_SUCCESS_BOARD, response);
    }

    /**
     * @param pageable : 모든 정보를 읽을 수 있도록 page를 넘긴다
     * @return PageDto : 읽은 Board에 대한 정보를 가진 dto
     * */
    @GetMapping("/all")
    public ResponseEntity<?> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        var response = boardService.readAll(pageable);

        return ResponseDto.toResponseEntity(ResponseMessage.READ_SUCCESS_ALL_BOARD, response);
    }

    /*@GetMapping("/solution")
    public ResponseEntity<?> readSolutionBoard(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            BoardReadAllRequest request
    ) {
        var response = boardService.readSolutionBoard(pageable, request);

        return PageDto.ok(response);
    }*/


    /**
     * @param request : update할 Board에 대한 정보를 가진 dto
     * @return BoardResponse : Update한 Board에 대한 정보를 가진 dto
     * */
    @PutMapping
    public ResponseEntity<?> update(@RequestBody BoardUpdateRequest request) {
        var response = boardService.update(request);

        return ResponseDto.toResponseEntity(ResponseMessage.UPDATE_SUCCESS_BOARD, response);
    }


    /**
     * @param request : 삭제할 Board에 대한 정보를 가진 dto
     * @return BoardResponse : 삭제된 Board에 대한 정보를 가진 dto
     * */
    @DeleteMapping
    public ResponseEntity<ResponseDto<BoardResponse>> delete(@RequestBody BoardDeleteRequest request) {
        BoardResponse delete = boardService.delete(request);

        return ResponseDto.toResponseEntity(ResponseMessage.DELETE_SUCCESS_BOARD,delete);
    }
}
