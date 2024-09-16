package com.example.project.compile.controller.rest;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import com.example.project.compile.dto.CompileRequest;
import com.example.project.compile.service.CompileService;
import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compile")
public class CompileApiController {

    private final CompileService compileService;
    private final AuthTokenService authTokenService;

    @PostMapping
    public ResponseEntity<?> compileCode(@RequestHeader("code-for-code-auth") String token,
                                         @RequestBody CompileRequest request) {
        String jwt = token;

        if (authTokenService.isValidateToken(jwt)) {
            log.info("Token: {}", jwt);
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
        } else return ErrorResponseDto.of(ErrorMessage.NOT_FOUND_CLIENT_ID_HEADER);

    }
}

