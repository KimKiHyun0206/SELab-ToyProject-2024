package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class InvalidLoginUserIdException extends BusinessException {
    public InvalidLoginUserIdException() {
        super(ErrorMessage.INVALID_ID_TO_LOGIN);
    }
}