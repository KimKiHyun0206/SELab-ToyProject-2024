package com.example.project.auth.domain;

import com.example.project.user.domain.User;
import com.example.project.user.domain.vo.RoleType;
import lombok.Getter;

@Getter
public class UserDetail {
    private Long id;
    private String userEmail;
    private String name;
    private String userId;
    private RoleType roleType;

    public UserDetail(User user) {
        this.id = user.getId();
        this.userEmail = user.getEmail().getEmail();
        this.name = user.getName().getName();
        this.userId = user.getUserId();
        this.roleType = user.getRoleType();
    }

    public UserDetail() {
        this.roleType = RoleType.USER;
    }
}