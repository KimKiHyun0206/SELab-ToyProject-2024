package com.example.project.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserUpdateRequest {

    @NotNull
    private String userId;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String email;


}
