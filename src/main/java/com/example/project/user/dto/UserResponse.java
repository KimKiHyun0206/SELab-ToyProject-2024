package com.example.project.user.dto;

import com.example.project.restrictions.ResponseDto;
import com.example.project.user.domain.User;
import com.example.project.user.domain.vo.Email;
import com.example.project.user.domain.vo.Name;
import com.example.project.user.domain.vo.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse implements ResponseDto<User> {

    private Long id;
    private String userId;
    private String password;
    private Name name;
    private Long point;
    private Email email;
    private RoleType roleType;

    private boolean success;
    private String message;

    @Builder
    public UserResponse(Long id, String userId, String password, Name name, Long point, Email email, RoleType roleType, boolean success, String message) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.point = point;
        this.email = email;
        this.roleType = roleType;
    }

    public UserResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public User toEntity(){
        return new User(
                this.id,
                this.userId,
                this.password,
                this.name,
                this.point,
                this.email,
                this.roleType,
                true,
                null
        );
    }
}
