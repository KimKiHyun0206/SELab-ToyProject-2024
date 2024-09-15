package com.example.project.user.service;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.user.AlreadyExistUserEmailException;
import com.example.project.error.exception.user.InvalidIdToFindUserException;
import com.example.project.user.domain.Authority;
import com.example.project.user.domain.User;
import com.example.project.user.domain.vo.Email;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse register(UserRegisterRequest request) {
        duplicateValidationUserEmail(request.getEmail());

        return userRepository
                .save(request.toEntity())
                .toResponseDto();
    }


    @Transactional(readOnly = true)
    public void duplicateValidationUserEmail(String email) {
        userRepository.findByEmail(new Email(email))
                .ifPresent(member -> {
                    throw new AlreadyExistUserEmailException(ErrorMessage.ALREADY_EXIST_MEMBER_EMAIL_EXCEPTION, "이미 존재하는 이메일 정보입니다");
                });
    }

    @Transactional(readOnly = true)
    public UserResponse find(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidIdToFindUserException(ErrorMessage.USER_NOT_FOUND_ERROR, "유저를 찾을 수 없습니다"))
                .toResponseDto();
    }

    @Transactional
    public UserResponse updateUser(UserUpdateRequest request) {
        User user = userRepository
                .findByUserId(request.getUserId())
                .orElseThrow(() -> new InvalidIdToFindUserException(ErrorMessage.USER_NOT_FOUND_ERROR, "유저를 찾을 수 없습니다"));

        user.updateUser(request);

        return user.toResponseDto();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> readAllUser() {
        return userRepository.findAll().stream()
                .map(User::toResponseDto)
                .collect(Collectors.toList());
    }
}