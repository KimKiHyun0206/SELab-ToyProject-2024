package com.example.project.error.exception.board;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class AlreadyExistBoardNameException extends BusinessException {
    public AlreadyExistBoardNameException(ErrorMessage message) {
        super(message);
    }

    public AlreadyExistBoardNameException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public AlreadyExistBoardNameException(String reason) {
        super(reason);
    }
}
