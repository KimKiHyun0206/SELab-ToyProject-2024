package com.example.project.error.exception.board;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class InvalidBoardIdException extends BusinessException {

    public InvalidBoardIdException(){
        super(ErrorMessage.BOARD_NOT_FOUND_ERROR, "Board ID가 올바르지 않습니다");
    }

    public InvalidBoardIdException(ErrorMessage message) {
        super(message);
    }

    public InvalidBoardIdException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public InvalidBoardIdException(String reason) {
        super(reason);
    }
}
