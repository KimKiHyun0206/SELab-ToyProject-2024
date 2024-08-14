package com.example.project.error.exception.user;

import com.example.project.error.exception.BusinessException;
import com.example.project.error.dto.ErrorMessage;

public class InvalidNameException extends BusinessException {
    public InvalidNameException(ErrorMessage message) {
        super(message);
    }

    public InvalidNameException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public InvalidNameException(String reason) {
        super(reason);
    }
}