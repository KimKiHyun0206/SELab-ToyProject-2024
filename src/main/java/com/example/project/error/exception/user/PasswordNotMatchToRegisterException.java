package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class PasswordNotMatchToRegisterException extends BusinessException {
    public PasswordNotMatchToRegisterException() {
        super(ErrorMessage.INVALID_PASSWORD_MATCH_TO_REGISTER_EXCEPTION);
    }
}