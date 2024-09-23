package com.example.project.error.exception.board;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class AlreadyExistBoardNameException extends BusinessException {
    public AlreadyExistBoardNameException() {
        super(ErrorMessage.DUPLICATE_BOARD_NAME_DUPLICATE);
    }
}
