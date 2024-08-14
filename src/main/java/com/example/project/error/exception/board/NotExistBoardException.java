package com.example.project.error.exception.board;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class NotExistBoardException extends BusinessException {

    public NotExistBoardException() {
        super(ErrorMessage.BOARD_NOT_FOUND_ERROR,"Board 가 존재하지 않습니다");
    }

    public NotExistBoardException(ErrorMessage message) {
        super(message);
    }

    public NotExistBoardException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public NotExistBoardException(String reason) {
        super(reason);
    }
}
