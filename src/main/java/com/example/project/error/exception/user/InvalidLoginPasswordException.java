package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class InvalidLoginPasswordException extends BusinessException {
    public InvalidLoginPasswordException() {
        super(ErrorMessage.INVALID_PASSWORD_TO_LOGIN);
    }
}