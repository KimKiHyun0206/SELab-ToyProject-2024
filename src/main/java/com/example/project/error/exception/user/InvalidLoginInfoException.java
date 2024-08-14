package com.example.project.error.exception.user;


import com.example.project.error.exception.BusinessException;
import com.example.project.error.dto.ErrorMessage;

public class InvalidLoginInfoException extends BusinessException {
    public InvalidLoginInfoException(ErrorMessage message) {
        super(message);
    }

    public InvalidLoginInfoException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public InvalidLoginInfoException(String reason) {
        super(reason);
    }
}