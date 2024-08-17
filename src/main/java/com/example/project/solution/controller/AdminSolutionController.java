package com.example.project.solution.controller;

import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import com.example.project.solution.dto.response.SolutionResponse;
import com.example.project.solution.dto.request.admin.SolutionDeleteRequest;
import com.example.project.solution.dto.request.admin.SolutionRegisterRequest;
import com.example.project.solution.dto.request.admin.SolutionUpdateRequest;
import com.example.project.solution.service.AdminSolutionService;
import com.example.project.user.domain.vo.RoleType;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.service.LoginService;
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
    private final LoginService loginService;

    /**
     * @param request : Solution 을 Update 할 수 있도록 정보를 가진 dto
     * @return SolutionResponse : Update 된 Solution 에 대한 정보를 가지는 dto
     */
    @PatchMapping
    public ResponseEntity<?> update(@RequestBody SolutionUpdateRequest request) {
        UserResponse login = loginService.login(new LoginRequest(request.getId(), request.getPassword()));

        if(login.getRoleType() == RoleType.ADMIN){
            var response = service.updateAll(request);


            log.info("Admin {} -> Solution {} Update", request.getAdminId(), request.getSolutionId());
            return ResponseDto.toResponseEntity(ResponseMessage.UPDATE_SUCCESS_SOLUTION, response);
        }

        return null;
    }

    /**
     * @param request : Solution 을 삭제할 수 있는 정보를 가진 dto
     * @return SolutionResponse : 삭제된 Solution 에 대한 정보를 가지는 dto
     */
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody SolutionDeleteRequest request) {
        UserResponse login = loginService.login(new LoginRequest(request.getId(), request.getPassword()));

        if (login.getRoleType() == RoleType.ADMIN){
            SolutionResponse response = service.delete(request);
            if (response != null) {
                return ResponseDto.toResponseEntity(ResponseMessage.DELETE_SUCCESS_SOLUTION, response);
            }

            log.info("Admin {} -> Solution {} Delete", request.getAdminId(), request.getSolutionId());
            return ResponseDto.toResponseEntity(ResponseMessage.DELETE_FAIL_SOLUTION, null);
        }

        log.info("Solution Delete : Request Id is Not Admin");
        return null;
    }

    /**
     * @param request : Solution 을 등록할 수 있는 정보를 가진 dto
     * @return SolutionResponse : 등록된 Solution 에 대한 정보를 가지는 dto
     */
    //TODO Admin 인 것을 확인하는 부분 추가해야함
    @GetMapping
    public ResponseEntity<?> register(@RequestBody SolutionRegisterRequest request) {
        UserResponse login = loginService.login(new LoginRequest(request.getId(), request.getPassword()));
        var response = service.register(request);


        log.info("Admin {} -> Solution {} Register", request.getAdminId(), response.getId());
        return ResponseDto.toResponseEntity(ResponseMessage.CREATE_SUCCESS_SOLUTION, response);
    }

}
