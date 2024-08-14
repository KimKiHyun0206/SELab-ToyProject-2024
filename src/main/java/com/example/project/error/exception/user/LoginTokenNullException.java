package com.example.project.error.exception.user;

import com.example.project.error.exception.BusinessException;
import com.example.project.error.dto.ErrorMessage;

public class LoginTokenNullException extends BusinessException {
    public LoginTokenNullException(ErrorMessage message) {
        super(message);
    }

    public LoginTokenNullException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public LoginTokenNullException(String reason) {
        super(reason);
    }
}