package com.example.project.error;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.dto.ErrorResponseDto;
import com.example.project.error.exception.BusinessException;
import com.example.project.error.exception.ViewException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ViewExceptionHandler {
    @ExceptionHandler(ViewException.class)
    protected String handleBusinessException(BusinessException e) {
        log.error("[ERROR] ViewException -> {}", e.getErrorMessage().getMessage());

        return "error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errorMessage = ErrorMessage.INVALID_REQUEST_PARAMETER;

        log.error("[ERROR] MethodArgumentNotValidException -> {}", e.getBindingResult());

        return ErrorResponseDto.of(errorMessage);
    }
}
