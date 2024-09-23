package com.example.project.error.exception.user;

import com.example.project.error.exception.BusinessException;
import com.example.project.error.dto.ErrorMessage;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException() {
        super(ErrorMessage.INVALID_PASSWORD_REGEX_EXCEPTION);
    }
}