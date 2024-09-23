package com.example.project.error.exception.board;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class BoardIdNotMatchException extends BusinessException {
    public BoardIdNotMatchException() {
        super(ErrorMessage.ID_NOT_MATCH_TO_DELETE_BOARD);
    }
}
