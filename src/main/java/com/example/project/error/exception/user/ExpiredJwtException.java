package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class ExpiredJwtException extends BusinessException {
    public ExpiredJwtException(ErrorMessage message) {
        super(message);
    }

    public ExpiredJwtException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public ExpiredJwtException(String reason) {
        super(reason);
    }
}