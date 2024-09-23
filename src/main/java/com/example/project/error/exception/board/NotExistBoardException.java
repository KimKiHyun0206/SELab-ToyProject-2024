package com.example.project.error.exception.board;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class NotExistBoardException extends BusinessException {
    public NotExistBoardException() {
        super(ErrorMessage.BOARD_NOT_FOUND_ERROR);
    }
}