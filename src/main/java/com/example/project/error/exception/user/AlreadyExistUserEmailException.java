package com.example.project.error.exception.user;

import com.example.project.error.exception.BusinessException;
import com.example.project.error.dto.ErrorMessage;

public class AlreadyExistUserEmailException extends BusinessException {

    public AlreadyExistUserEmailException(String duplicateEmail) {
        super(ErrorMessage.ALREADY_EXIST_MEMBER_EMAIL_EXCEPTION, "중복된 이메일 : " + duplicateEmail);
    }
}
