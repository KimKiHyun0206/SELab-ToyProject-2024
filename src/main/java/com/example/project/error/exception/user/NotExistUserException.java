package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class NotExistUserException extends BusinessException {
    public NotExistUserException(ErrorMessage message) {
        super(message);
    }

    public NotExistUserException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public NotExistUserException(String reason) {
        super(reason);
    }
}