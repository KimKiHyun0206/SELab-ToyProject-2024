package com.example.project.error.exception.user;

import com.example.project.error.exception.BusinessException;
import com.example.project.error.dto.ErrorMessage;

public class InvalidNameException extends BusinessException {
    public InvalidNameException() {
        super(ErrorMessage.INVALID_NAME_REGEX_EXCEPTION);
    }
}