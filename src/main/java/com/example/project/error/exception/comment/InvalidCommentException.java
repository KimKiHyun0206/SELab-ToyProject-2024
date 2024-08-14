package com.example.project.error.exception.comment;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class InvalidCommentException extends BusinessException {
    public InvalidCommentException(ErrorMessage message) {
        super(message);
    }

    public InvalidCommentException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public InvalidCommentException(String reason) {
        super(reason);
    }
}
