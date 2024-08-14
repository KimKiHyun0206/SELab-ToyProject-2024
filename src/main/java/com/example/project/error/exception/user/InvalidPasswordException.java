package com.example.project.error.exception.user;

import com.example.project.error.exception.BusinessException;
import com.example.project.error.dto.ErrorMessage;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException(ErrorMessage message) {
        super(message);
    }

    public InvalidPasswordException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public InvalidPasswordException(String reason) {
        super(reason);
    }
}