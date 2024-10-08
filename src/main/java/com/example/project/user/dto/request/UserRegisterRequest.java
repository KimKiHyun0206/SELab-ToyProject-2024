package com.example.project.user.dto.request;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.user.InvalidPasswordMatchException;
import com.example.project.restrictions.RegisterRequest;
import com.example.project.user.domain.User;
import com.example.project.user.domain.vo.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterRequest implements RegisterRequest<User> {
    @NotNull
    private String userId;
    @NotNull
    private String password;
    @NotNull
    private String repeatPassword;
    @NotNull
    private String name;
    @NotNull
    private String email;

    @Override
    public User toEntity(){
        return User.builder()
                .email(email)
                .userId(userId)
                .password(password)
                .name(name)
                .point(0L)
                .roleType(RoleType.USER)
                .build();
    }

    private void matchingPassword(){
        if(!this.password.equals(this.repeatPassword)) throw new InvalidPasswordMatchException(ErrorMessage.INVALID_PASSWORD_MATCH_TO_REGISTER_EXCEPTION, "회원가입을 하기 위한 비밀번호가 일치하지 않습니다");
    }
}
