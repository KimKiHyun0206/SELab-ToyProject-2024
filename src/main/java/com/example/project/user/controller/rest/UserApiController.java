package com.example.project.user.controller.rest;

import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {
    private final UserService userService;

    /**
     * @param email : 회원가입 중복확인을 위한 email String
     * @return boolean 값 리턴. 실패했다면 Exception 발행
     */
    @GetMapping("/email-check")
    public ResponseEntity<?> checkEmailForSignUp(@RequestParam String email) {
        userService.duplicateValidationUserEmail(email);    //실패시 AlreadyExistUserEmailException 발생

        return ResponseDto.toResponseEntity(ResponseMessage.SUCCESS_SIGN_UP_EMAIL_CHECK, true);
    }

    /**
     * @param request : 회원가입 유저 정보 Dto
     * @return UserResponse : User 정보 ResponseDto
     */
    @PostMapping("/signup")
    public ResponseEntity<?> joinMember(@RequestBody @Valid UserRegisterRequest request) {
        var response = userService.register(request);

        return ResponseDto.toResponseEntity(ResponseMessage.CREATE_SUCCESS_USER, response);
    }

    /**
     * @param detail  유저에 대한 자세한 정보를 가진다
     * @param request 요청에 대한 정보를 가진다
     * @return UserResponse : User 정보 ResponseDto
     */
    @PatchMapping("/edit")
    public ResponseEntity<?> editMember(@RequestBody @Valid UserUpdateRequest request) {
        var response = userService.updateUser(request);

        return ResponseDto.toResponseEntity(ResponseMessage.SUCCESS_UPDATE_USER, response);
    }

    @GetMapping
    public ResponseEntity<?> searchAllMember() {
        var response = userService.readAllUser();

        return ResponseDto.toResponseEntity(ResponseMessage.SUCCESS_SEARCH_ALL_USER, response);
    }
}