package com.example.project.auth.domain;

import com.example.project.user.domain.vo.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Authentication {
    private UserDetail userDetail;
    private RoleType roleType;
}