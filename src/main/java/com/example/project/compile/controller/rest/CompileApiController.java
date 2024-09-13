package com.example.project.compile.controller.rest;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import com.example.project.compile.dto.CompileRequest;
import com.example.project.compile.service.CompileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> compileCode(@RequestHeader("Authorization") String token,
                                         @RequestBody CompileRequest request) {
        String jwt = token.replace("Bearer ", "");

        if (!authTokenService.isValidateToken(jwt)) {
            log.info("Token: {}", jwt);
            return ResponseDto.toResponseEntity(ResponseMessage.INVALID_TOKEN_EXCEPTION, jwt);
        }

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

