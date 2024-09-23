package com.example.project.error.exception.comment;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class CommentIdNotMatchException extends BusinessException {
    public CommentIdNotMatchException() {
        super(ErrorMessage.ID_NOT_MATCH_TO_DELETE_COMMENT);
    }
}