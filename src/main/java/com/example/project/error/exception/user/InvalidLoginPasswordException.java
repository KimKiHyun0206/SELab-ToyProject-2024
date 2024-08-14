package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class InvalidLoginPasswordException extends BusinessException {
    public InvalidLoginPasswordException(ErrorMessage message) {
        super(message);
    }

    public InvalidLoginPasswordException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public InvalidLoginPasswordException(String reason) {
        super(reason);
    }
}
