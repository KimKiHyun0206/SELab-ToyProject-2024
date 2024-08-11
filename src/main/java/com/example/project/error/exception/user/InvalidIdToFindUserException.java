package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class InvalidIdToFindUserException extends BusinessException {
    public InvalidIdToFindUserException(ErrorMessage message) {
        super(message);
    }

    public InvalidIdToFindUserException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public InvalidIdToFindUserException(String reason) {
        super(reason);
    }
}
