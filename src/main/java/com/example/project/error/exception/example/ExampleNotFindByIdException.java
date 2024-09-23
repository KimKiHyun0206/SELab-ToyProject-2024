package com.example.project.error.exception.example;

import com.example.project.error.exception.BusinessException;

import static com.example.project.error.dto.ErrorMessage.EXAMPLE_NOT_FIND_BY_ID;

public class ExampleNotFindByIdException extends BusinessException {
    public ExampleNotFindByIdException(){
        super(EXAMPLE_NOT_FIND_BY_ID);
    }
}