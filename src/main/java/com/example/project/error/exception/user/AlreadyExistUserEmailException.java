package com.example.project.error.exception.user;

import com.example.project.error.exception.BusinessException;
import com.example.project.error.dto.ErrorMessage;

public class AlreadyExistUserEmailException extends BusinessException {

    public AlreadyExistUserEmailException(ErrorMessage message) {
        super(message);
    }

    public AlreadyExistUserEmailException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public AlreadyExistUserEmailException(String reason) {
        super(reason);
    }
}
