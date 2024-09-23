package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class InvalidIdToFindUserException extends BusinessException {
    public InvalidIdToFindUserException() {
        super(ErrorMessage.USER_NOT_FOUND_ERROR);
    }
}