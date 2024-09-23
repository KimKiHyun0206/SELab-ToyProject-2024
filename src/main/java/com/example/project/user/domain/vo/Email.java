package com.example.project.user.domain.vo;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.user.InvalidEmailException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@Embeddable
public class Email {
    private static final String EMAIL_REGEX = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private String email;

    public Email(String email) {
        validateEmail(email);
    }

    protected Email() {
    }

    private void validateEmail(String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new InvalidEmailException();
        }
        this.email = email;
    }
}