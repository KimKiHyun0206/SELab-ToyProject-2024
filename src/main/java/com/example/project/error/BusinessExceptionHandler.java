package com.example.project.error;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.dto.ErrorResponseDto;
import com.example.project.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice   //REST API 통신에서 발생하는 에러 메시지를 통신에서 제공한다 -> TODO ControllerAdvice를 만들기
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException e) {
        var errorMessage = e.getErrorMessage();

        log.error("[ERROR] BusinessException -> {}", errorMessage.getMessage());

        return ErrorResponseDto.of(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errorMessage = ErrorMessage.INVALID_REQUEST_PARAMETER;

        log.error("[ERROR] MethodArgumentNotValidException -> {}", e.getBindingResult());

        return ErrorResponseDto.of(errorMessage);
    }
}