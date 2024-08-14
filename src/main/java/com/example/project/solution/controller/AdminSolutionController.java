package com.example.project.solution.controller;

import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import com.example.project.solution.dto.SolutionResponse;
import com.example.project.solution.dto.request.admin.SolutionDeleteRequest;
import com.example.project.solution.dto.request.admin.SolutionRegisterRequest;
import com.example.project.solution.dto.request.admin.update.SolutionContextUpdateRequest;
import com.example.project.solution.dto.request.admin.update.SolutionDifficultyUpdateRequest;
import com.example.project.solution.dto.request.admin.update.SolutionExampleUpdateRequest;
import com.example.project.solution.dto.request.admin.update.SolutionUpdateRequest;
import com.example.project.solution.service.AdminSolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/solution/admin")
@RequiredArgsConstructor
public class AdminSolutionController {

    private final AdminSolutionService service;

    /**
     * @param request : Solution 의 Context 부분을 Update 할 수 있도록 정보를 가진 dto
     * @return SolutionResponse : Update 된 Solution 에 대한 정보를 가지는 dto
     * */
    @PatchMapping("/context")
    public ResponseEntity<?> contextUpdate(@RequestBody SolutionContextUpdateRequest request) {
        var response = service.contextUpdate(request);

        return ResponseDto.toResponseEntity(ResponseMessage.UPDATE_SUCCESS_SOLUTION, response);
    }

    /**
     * @param request : Solution 의 Difficulty 부분을 Update 할 수 있도록 정보를 가진 dto
     * @return SolutionResponse : Update 된 Solution 에 대한 정보를 가지는 dto
     * */
    @PatchMapping("/difficulty")
    public ResponseEntity<?> difficultyUpdate(@RequestBody SolutionDifficultyUpdateRequest request) {
        var response = service.difficultyUpdate(request);

        return ResponseDto.toResponseEntity(ResponseMessage.UPDATE_SUCCESS_SOLUTION, response);
    }

    /**
     * @param request : Solution 의 Example 부분을 Update 할 수 있도록 정보를 가진 dto
     * @return SolutionResponse : Update 된 Solution 에 대한 정보를 가지는 dto
     * */
    @PatchMapping("/example")
    public ResponseEntity<?> exampleUpdate(@RequestBody SolutionExampleUpdateRequest request) {
        var response = service.exampleUpdate(request);

        return ResponseDto.toResponseEntity(ResponseMessage.UPDATE_SUCCESS_SOLUTION, response);
    }

    /**
     * @param request : Solution 을 Update 할 수 있도록 정보를 가진 dto
     * @return SolutionResponse : Update 된 Solution 에 대한 정보를 가지는 dto
     * */
    @PatchMapping
    public ResponseEntity<?> update(@RequestBody SolutionUpdateRequest request) {
        var response = service.updateAll(request);

        return ResponseDto.toResponseEntity(ResponseMessage.UPDATE_SUCCESS_SOLUTION, response);
    }

    /**
     * @param request : Solution 을 삭제할 수 있는 정보를 가진 dto
     * @return SolutionResponse : 삭제된 Solution 에 대한 정보를 가지는 dto
     * */
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody SolutionDeleteRequest request) {
        SolutionResponse response = service.delete(request);
        if (response != null) {
            return ResponseDto.toResponseEntity(ResponseMessage.DELETE_SUCCESS_SOLUTION, response);
        }

        return ResponseDto.toResponseEntity(ResponseMessage.DELETE_FAIL_SOLUTION, null);
    }

    /**
     * @param request : Solution 을 등록할 수 있는 정보를 가진 dto
     * @return SolutionResponse : 등록된 Solution 에 대한 정보를 가지는 dto
     * */
    @GetMapping
    public ResponseEntity<?> register(@RequestBody SolutionRegisterRequest request) {
        var response = service.register(request);

        return ResponseDto.toResponseEntity(ResponseMessage.CREATE_SUCCESS_SOLUTION, response);
    }

}
