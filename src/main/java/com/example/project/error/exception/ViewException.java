package com.example.project.error.exception;

import com.example.project.error.dto.ErrorMessage;
import lombok.Getter;

@Getter
public class ViewException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public ViewException(ErrorMessage message) {
        super(message.getMessage());
        this.errorMessage = message;
    }

    public ViewException(ErrorMessage message, String reason) {
        super(reason);
        this.errorMessage = message;
    }

    public ViewException(String reason) {
        super(reason);
        this.errorMessage = ErrorMessage.INTERNAL_SERVER_ERROR;
    }
}
