package com.example.project.user.service;

import com.example.project.auth.domain.UserDetail;
import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.user.AlreadyExistUserEmailException;
import com.example.project.error.exception.user.InvalidLoginInfoException;
import com.example.project.error.exception.user.LoginTokenNullException;
import com.example.project.user.domain.User;
import com.example.project.user.domain.vo.Email;
import com.example.project.user.domain.vo.RoleType;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse register(UserRegisterRequest request){
        duplicateValidationUserEmail(request.getEmail());

        var response = userRepository.save(request.toEntity());

        return response.toResponseDto();
    }

    @Transactional(readOnly = true)
    public void duplicateValidationUserEmail(String email){
        userRepository.findByEmail(new Email(email))
                .ifPresent(member -> {
                    throw new AlreadyExistUserEmailException(ErrorMessage.ALREADY_EXIST_MEMBER_EMAIL_EXCEPTION, "이미 존재하는 이메일 정보입니다");
                });
    }

    @Transactional
    public UserResponse read(Long id){
        return userRepository.findById(id).get().toResponseDto();
    }


    @Transactional
    public UserResponse updateUser(UserDetail detail, UserUpdateRequest request) {
        loginCheckException(detail);
        var user = findByIdFromLogin(detail.getId());
        user.updateUser(request);
        userRepository.save(user);

        return user.toResponseDto();
    }

    public void loginCheckException(UserDetail detail) {
        if(detail.getRoleType() == RoleType.USER) {
            throw new LoginTokenNullException(ErrorMessage.NOT_LOGIN_USER_EXCEPTION, "로그인 정보가 없습니다");
        }
    }

    @Transactional(readOnly = true)
    public User findByIdFromLogin(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidLoginInfoException(ErrorMessage.INVALID_LOGIN_USER_INFORMATION_EXCEPTION, "잘못된 유저 로그인 정보입니다"));
    }

    public List<UserResponse> readAllUser() {
        return userRepository.findAll().stream()
                .map(User::toResponseDto)
                .collect(Collectors.toList());
    }
}
