package com.example.project.user.service;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.user.InvalidLoginUserIdException;
import com.example.project.error.exception.user.InvalidLoginPasswordException;
import com.example.project.user.domain.User;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse login(LoginRequest request){
        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(
                ()->{
                    throw new InvalidLoginUserIdException(ErrorMessage.INVALID_ID_TO_LOGIN, "ID로 유저를 찾을 수 없습니다");
                }
        );

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return user.toResponseDto();
        }else throw new InvalidLoginPasswordException(ErrorMessage.INVALID_PASSWORD_TO_LOGIN,"PASSWORD가 일치하지 않습니다");
    }
}
