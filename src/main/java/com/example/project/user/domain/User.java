package com.example.project.user.domain;

import com.example.project.common.BaseEntity;
import com.example.project.user.domain.converter.PasswordEncodeConverter;
import com.example.project.user.domain.vo.Email;
import com.example.project.user.domain.vo.Name;
import com.example.project.user.domain.vo.RoleType;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column
    @Convert(converter = PasswordEncodeConverter.class)
    private String password;

    @Column
    @Embedded
    private Name name;

    @Column
    private Long point;

    @Column
    @Embedded
    private Email email;

    @Column(name = "role_type")
    @Enumerated
    private RoleType roleType;



    @Builder
    public User(String email,String userId,  String password, String name){
        this.userId = userId;
        this.email = new Email(email);
        this.password = password;
        this.name = new Name(name);
    }

    public UserResponse toResponseDto(){
        return UserResponse.builder()
                .id(id)
                .userId(userId)
                .password(password)
                .name(name)
                .point(point)
                .email(email)
                .build();
    }

    public void updateUser(UserUpdateRequest request){
        this.email = new Email(request.getEmail());
        this.password = request.getPassword();
        this.name = new Name(request.getName());
        this.userId = request.getUserId();
    }

    public void addPoint(){
        this.point++;
    }

}