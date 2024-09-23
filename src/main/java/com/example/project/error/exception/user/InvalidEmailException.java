package com.example.project.error.exception.user;


import com.example.project.error.exception.BusinessException;
import com.example.project.error.dto.ErrorMessage;

public class InvalidEmailException extends BusinessException {
    public InvalidEmailException() {
        super(ErrorMessage.INVALID_EMAIL_REGEX_EXCEPTION);
    }
}