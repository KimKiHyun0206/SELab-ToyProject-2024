package com.example.project.error.exception.board;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class BoardIdNotMatchException extends BusinessException {
    public BoardIdNotMatchException(ErrorMessage message) {
        super(message);
    }

    public BoardIdNotMatchException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public BoardIdNotMatchException(String reason) {
        super(reason);
    }
}
