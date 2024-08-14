package com.example.project.error.exception.user;


import com.example.project.error.exception.BusinessException;
import com.example.project.error.dto.ErrorMessage;

public class InvalidEmailException extends BusinessException {
    public InvalidEmailException(ErrorMessage message) {
        super(message);
    }

    public InvalidEmailException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public InvalidEmailException(String reason) {
        super(reason);
    }
}