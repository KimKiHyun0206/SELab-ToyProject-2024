package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class InvalidLoginUserIdException extends BusinessException {
    public InvalidLoginUserIdException(ErrorMessage message) {
        super(message);
    }

    public InvalidLoginUserIdException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public InvalidLoginUserIdException(String reason) {
        super(reason);
    }
}
