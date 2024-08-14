package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class InvalidPasswordMatchException extends BusinessException {
    public InvalidPasswordMatchException(ErrorMessage message) {
        super(message);
    }

    public InvalidPasswordMatchException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public InvalidPasswordMatchException(String reason) {
        super(reason);
    }
}