package com.example.project.error.exception.comment;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class InvalidCommentException extends BusinessException {
    public InvalidCommentException() {
        super(ErrorMessage.NOT_EXIST_COMMENT_EXCEPTION);
    }
}