package com.example.project.user.domain;

import com.example.project.common.BaseEntity;
import com.example.project.restrictions.Domain;
import com.example.project.user.domain.converter.PasswordEncodeConverter;
import com.example.project.user.domain.vo.Email;
import com.example.project.user.domain.vo.Name;
import com.example.project.user.domain.vo.RoleType;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Domain<UserResponse> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
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

    @Column
    private Boolean activated;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;


    @Builder
    public User(String email, String userId, String password, String name, Long point, RoleType roleType, Boolean activated, Set<Authority> authorities) {
        this.userId = userId;
        this.email = new Email(email);
        this.password = password;
        this.name = new Name(name);
        this.point = point;
        this.roleType = roleType;
        this.activated = activated;
        this.authorities = authorities;
    }

    @Override
    public UserResponse toResponseDto() {
        return UserResponse.builder()
                .id(id)
                .userId(userId)
                .password(password)
                .name(name)
                .point(point)
                .email(email)
                .roleType(roleType)
                .build();
    }

    public void updateUser(UserUpdateRequest request) {
        this.email = new Email(request.getEmail());
        this.password = request.getPassword();
        this.name = new Name(request.getName());
        this.userId = request.getUserId();
    }

    public void addPoint() {
        this.point++;
    }

}