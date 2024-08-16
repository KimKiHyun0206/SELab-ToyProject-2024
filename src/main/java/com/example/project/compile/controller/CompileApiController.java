package com.example.project.compile.controller;

import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import com.example.project.solution.dto.request.user.SolutionCompileRequest;
import com.example.project.compile.service.CompileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compile")
public class CompileApiController {

    private final CompileService compileService;

    @PostMapping
    public ResponseEntity<?> compileCode(@RequestBody SolutionCompileRequest request) {
        try {
            String result = compileService.compileAndRun(request.getLanguage(), request.getCode());
            return ResponseDto.toResponseEntity(ResponseMessage.COMPILE_SUCCESS, result);
        } catch (IllegalArgumentException e) {
            return ResponseDto.toResponseEntity(ResponseMessage.INVALID_LANGUAGE, e.getMessage());
        } catch (IOException e) {
            return ResponseDto.toResponseEntity(ResponseMessage.IO_ERROR, e.getMessage());
        } catch (InterruptedException e) {
            return ResponseDto.toResponseEntity(ResponseMessage.EXECUTION_INTERRUPTED, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.toResponseEntity(ResponseMessage.GENERAL_COMPILE_ERROR, e.getMessage());
        }
    }
}

