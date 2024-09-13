package com.example.project.error.exception.util;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class NotFoundClientIdHeaderException extends BusinessException {

    public NotFoundClientIdHeaderException(ErrorMessage message) {
        super(message);
    }

    public NotFoundClientIdHeaderException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public NotFoundClientIdHeaderException(String reason) {
        super(reason);
    }
}
