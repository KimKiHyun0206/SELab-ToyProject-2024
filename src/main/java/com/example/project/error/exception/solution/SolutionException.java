package com.example.project.error.exception.solution;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class SolutionException extends BusinessException {
    public SolutionException() {
        super(ErrorMessage.SOLUTION_NOT_FOUND_ERROR);
    }
}