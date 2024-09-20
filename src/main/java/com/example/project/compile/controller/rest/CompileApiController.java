package com.example.project.compile.controller.rest;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import com.example.project.common.util.HeaderUtil;
import com.example.project.compile.dto.CompileRequest;
import com.example.project.compile.service.CompileService;
import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    public ResponseEntity<?> compileCode(@RequestBody CompileRequest request, HttpServletRequest httpServletRequest, @Value("${compile.url}") String codeDir) throws IOException {
        if (authTokenService.isValidateToken(HeaderUtil.resolveToken(httpServletRequest))) {
            String result = compileService.compileAndRun(
                    request.getLanguage(),
                    request.getCode(),
                    codeDir
            );
            return ResponseDto.toResponseEntity(ResponseMessage.COMPILE_SUCCESS, result);
        } else return ErrorResponseDto.of(ErrorMessage.NOT_FOUND_CLIENT_ID_HEADER);
    }
}