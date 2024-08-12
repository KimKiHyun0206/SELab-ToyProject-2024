package com.example.project.user.dto.request;

import com.example.project.restrictions.RegisterRequest;
import com.example.project.user.domain.User;
import com.example.project.user.domain.vo.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterRequest implements RegisterRequest<User> {
    @NotNull
    private String userId;
    @NotNull
    private String password;
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
}
