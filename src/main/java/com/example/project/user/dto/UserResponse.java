package com.example.project.user.dto;

import com.example.project.user.domain.vo.Email;
import com.example.project.user.domain.vo.Name;
import com.example.project.user.domain.vo.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String userId;
    private String password;
    private Name name;
    private Long point;
    private Email email;
    private RoleType roleType;

    @Builder
    public UserResponse(Long id, String userId, String password, Name name, Long point, Email email, RoleType roleType) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.point = point;
        this.email = email;
        this.roleType = roleType;
    }
}
