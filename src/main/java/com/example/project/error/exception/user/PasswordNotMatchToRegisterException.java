package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class PasswordNotMatchToRegisterException extends BusinessException {
    public PasswordNotMatchToRegisterException(ErrorMessage message) {
        super(message);
    }

    public PasswordNotMatchToRegisterException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public PasswordNotMatchToRegisterException(String reason) {
        super(reason);
    }
}
