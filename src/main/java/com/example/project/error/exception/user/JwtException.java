package com.example.project.error.exception.user;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class JwtException extends BusinessException {
    public JwtException(ErrorMessage message){
        super(message);
    }

    public JwtException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public JwtException(String reason) {
        super(reason);
    }
}