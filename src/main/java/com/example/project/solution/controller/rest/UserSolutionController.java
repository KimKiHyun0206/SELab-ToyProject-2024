package com.example.project.solution.controller.rest;

import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import com.example.project.solution.dto.request.user.SolutionCompileRequest;
import com.example.project.solution.service.UserSolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/solutions/user")
@RequiredArgsConstructor
public class UserSolutionController {
    private final UserSolutionService service;

    /**
     * @return SolutionResponse : 찾은 문제에 대한 정보를 가진 dto
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findSolution(@PathVariable Long id) {
        var response = service.read(id);

        return ResponseDto.toResponseEntity(ResponseMessage.READ_SUCCESS_SOLUTION, response);
    }
}