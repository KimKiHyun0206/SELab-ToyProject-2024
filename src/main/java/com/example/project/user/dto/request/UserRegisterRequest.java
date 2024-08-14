package com.example.project.user.dto.request;

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
    private String name;
    @NotNull
    private String email;
    @NotNull
    private Long point = 0L;
    @NotNull
    private RoleType roleType = RoleType.USER;

    @Override
    public User toEntity(){
        return User.builder()
                .email(email)
                .userId(userId)
                .password(password)
                .name(name)
                .point(point)
                .roleType(roleType)
                .build();
    }
}
