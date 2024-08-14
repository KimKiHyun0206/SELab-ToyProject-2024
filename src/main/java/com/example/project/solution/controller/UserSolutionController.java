package com.example.project.solution.controller;

import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import com.example.project.solution.dto.request.user.SolutionCompileRequest;
import com.example.project.solution.dto.request.user.SolutionFindRequest;
import com.example.project.solution.service.UserSolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/solution/user")
@RequiredArgsConstructor
public class UserSolutionController {

    private final UserSolutionService service;

    /*@GetMapping("/difficulty")
    public ResponseEntity<?> findByDifficulty
            (
                    @RequestBody DifficultyFindRequest request,
                    @PageableDefault(sort = "solved", direction = Sort.Direction.DESC) Pageable pageable
            ) {
        var byDifficulty = service.findByDifficulty(request);

        return PageDto.ok(byDifficulty);
    }*/

    /**
     * @param request : 컴파일할 코드를 가진 dto
     * TODO 컴파일 부분을 완성하여 리턴값을 가져올 수 있도록 해야한다
     * */
    @PostMapping
    public ResponseEntity<?> compile(@RequestBody SolutionCompileRequest request){
        return null;
    }

    /**
     * @param request : Solution에 대한 정보를 찾는 요청을 하는 dto
     * @return SolutionResponse : 찾은 문제에 대한 정보를 가진 dto
     * */
    @GetMapping
    public ResponseEntity<?> findSolution(@RequestBody SolutionFindRequest request){
        var response = service.read(request);

        return ResponseDto.toResponseEntity(ResponseMessage.READ_SUCCESS_SOLUTION, response);
    }
}
