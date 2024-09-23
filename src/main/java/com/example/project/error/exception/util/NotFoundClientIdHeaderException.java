package com.example.project.error.exception.util;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class NotFoundClientIdHeaderException extends BusinessException {

    public NotFoundClientIdHeaderException() {
        super(ErrorMessage.NOT_FOUND_CLIENT_ID_HEADER);
    }
}
