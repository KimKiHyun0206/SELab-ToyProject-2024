package com.example.project.error.exception.comment;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class NotExistCommentException extends BusinessException {
    public NotExistCommentException() {
        super(ErrorMessage.NOT_EXIST_COMMENT_EXCEPTION);
    }
}
