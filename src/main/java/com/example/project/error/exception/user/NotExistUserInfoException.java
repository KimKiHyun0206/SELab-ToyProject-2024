package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;

import com.example.project.error.exception.BusinessException;


public class NotExistUserInfoException extends BusinessException {
    public NotExistUserInfoException(ErrorMessage message) {
        super(message);
    }

    public NotExistUserInfoException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public NotExistUserInfoException(String reason) {
        super(reason);
    }
}