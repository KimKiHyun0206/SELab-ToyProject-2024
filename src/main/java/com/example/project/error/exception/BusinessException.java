package com.example.project.error.exception;

import com.example.project.error.dto.ErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage message) {
        super(message.getMessage());
        this.errorMessage = message;
    }

    public BusinessException(ErrorMessage message, String reason) {
        super(reason);
        this.errorMessage = message;
    }

    public BusinessException(String reason) {
        super(reason);
        this.errorMessage = ErrorMessage.INTERNAL_SERVER_ERROR;
    }
}