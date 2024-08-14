package com.example.project.error.exception.comment;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class CommentIdNotMatchException extends BusinessException {
    public CommentIdNotMatchException(ErrorMessage message) {
        super(message);
    }

    public CommentIdNotMatchException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public CommentIdNotMatchException(String reason) {
        super(reason);
    }
}
