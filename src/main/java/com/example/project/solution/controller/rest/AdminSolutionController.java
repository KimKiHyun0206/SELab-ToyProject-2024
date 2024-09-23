package com.example.project.solution.controller.rest;

import com.example.project.solution.dto.request.admin.ExampleDeleteRequest;
import com.example.project.solution.dto.request.admin.ExampleRegisterRequest;
import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import com.example.project.solution.dto.request.admin.*;
import com.example.project.solution.dto.response.SolutionResponse;
import com.example.project.solution.service.AdminSolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/solutions/admin")
@RequiredArgsConstructor
public class AdminSolutionController {
    private final AdminSolutionService service;

    /**
     * @param request : Solution 을 Update 할 수 있도록 정보를 가진 dto
     * @return SolutionResponse : Update 된 Solution 에 대한 정보를 가지는 dto
     */
    @PatchMapping
    public ResponseEntity<?> update(@RequestBody SolutionUpdateRequest request) {
        var response = service.updateAll(request);

        log.info("Admin  -> Solution {} Update", request.getSolutionId());
        return ResponseDto.toResponseEntity(ResponseMessage.UPDATE_SUCCESS_SOLUTION, response);
    }

    /**
     * @param request : Solution 을 삭제할 수 있는 정보를 가진 dto
     * @return SolutionResponse : 삭제된 Solution 에 대한 정보를 가지는 dto
     */
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody SolutionDeleteRequest request) {
        SolutionResponse response = service.delete(request);
        if (response != null) {
            return ResponseDto.toResponseEntity(ResponseMessage.DELETE_SUCCESS_SOLUTION, response);
        }
        log.info("Admin  -> Solution {} Delete", request.getSolutionId());
        return ResponseDto.toResponseEntity(ResponseMessage.DELETE_FAIL_SOLUTION, null);
    }

    /**
     * @param request : Solution 을 등록할 수 있는 정보를 가진 dto
     * @return SolutionResponse : 등록된 Solution 에 대한 정보를 가지는 dto
     */
    @GetMapping
    public ResponseEntity<?> register(@RequestBody SolutionRegisterRequest request) {
        var response = service.register(request);
        log.info("Admin  -> Solution {} Register", response.getId());
        return ResponseDto.toResponseEntity(ResponseMessage.CREATE_SUCCESS_SOLUTION, response);
    }

    @PostMapping("/{solutionId}/examples")
    public ResponseEntity<?> addExampleToSolution(@PathVariable Long solutionId, @RequestBody ExampleRegisterRequest request) {
        request.setSolutionId(solutionId);
        service.addExampleToSolution(request);
        log.info("InputExample {}", solutionId);
        return ResponseDto.toResponseEntity(ResponseMessage.CREATE_SUCCESS_EXAMPLE, null);
    }

    @DeleteMapping("/{solutionId}/examples/{exampleId}")
    public ResponseEntity<?> removeExampleFromSolution(@PathVariable Long solutionId, @PathVariable Long exampleId) {
        ExampleDeleteRequest request = new ExampleDeleteRequest();
        request.setSolutionId(solutionId);
        request.setExampleId(exampleId);

        service.removeExampleFromSolution(request);
        log.info("RemoveExample {}", solutionId);
        return ResponseDto.toResponseEntity(ResponseMessage.DELETE_SUCCESS_SOLUTION, null);
    }
}