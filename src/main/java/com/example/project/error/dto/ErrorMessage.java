package com.example.project.error.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {
    /**
     * Server Error Message
     */
    CONFLICT_ERROR(HttpStatus.BAD_REQUEST, "예기치 못한 에러가 발생했습니다."),
    INTERNAL_SERVER_ERROR_BY_MAPPER_ERROR(HttpStatus.BAD_REQUEST, "예기치 못한 에러가 발생했습니다."),
    INTERNAL_SERVER_ERROR_BY_PROPERTIES_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예기치 못한 에러가 발생했습니다."),
    INVALID_REQUEST_PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "잘못된 요청 입니다."),

    USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "User 정보를 찾을 수 없습니다."),
    SOLUTION_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "Solution 정보를 찾을 수 없습니다"),
    BOARD_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "Board 정보를 찾을 수 없습니다"),
    /**
     * 서버 내부 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.BAD_REQUEST, "내부 서버 오류"),
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청 입니다."),
    SIGNATURE_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 서명입니다."),
    WRONG_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "잘못된 JWT 입니다"),
    EXPIRED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다"),

    //USER
    INVALID_EMAIL_REGEX_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 이메일 형식입니다"),
    INVALID_PASSWORD_REGEX_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 형식입니다"),
    INVALID_NAME_REGEX_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 이름 형식 입니다"),
    NOT_EXIST_USER_INFO_EXCEPTION(HttpStatus.NOT_FOUND, "유저 정보가 존재하지 않습니다"),
    NOT_EXIST_MEMBER_EXCEPTION(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다"),
    INVALID_PASSWORD_MATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "비밀번호가 맞지 않습니다"),
    ALREADY_EXIST_MEMBER_EMAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "이미 해당 이메일 정보가 등록되어 있습니다."),
    INVALID_LOGIN_USER_INFORMATION_EXCEPTION(HttpStatus.NOT_FOUND, "잘못된 로그인 정보입니다"),
    INVALID_PASSWORD_TO_LOGIN(HttpStatus.BAD_REQUEST, "Password가 틀렸습니다"),
    INVALID_ID_TO_LOGIN(HttpStatus.BAD_REQUEST, "ID가 일치하지 않습니다"),
    NOT_LOGIN_USER_EXCEPTION(HttpStatus.BAD_REQUEST, "로그인 정보가 없습니다"),
    NON_EXISTENT_BOARD_EXCEPTION(HttpStatus.BAD_REQUEST, "해당 보드가 존재하지 않습니다."),
    UNAUTHORIZED_ACCESS_EXCEPTION(HttpStatus.BAD_REQUEST, "권한이 없는 접근 입니다."),
    INVALID_DATA_ARGUMENT(HttpStatus.BAD_REQUEST, "invalid data argument"),
    FAILURE_FILE_CONVERT(HttpStatus.BAD_REQUEST, "이미지 업로드 실패"),

    //BOARD
    DUPLICATE_BOARD_NAME_DUPLICATE(HttpStatus.BAD_REQUEST, "중복된 이름을 가진 Board가 있습니다"),
    BOARD_NOT_EXIST(HttpStatus.NOT_FOUND, "Board 가 존재하지 않습니다"),
    ID_NOT_MATCH_TO_DELETE_BOARD(HttpStatus.BAD_REQUEST, "작성자와 요청자 ID가 일치하지 않아 삭제하지 못했습니다"),


    //COMMENT
    ID_NOT_MATCH_TO_DELETE_COMMENT(HttpStatus.BAD_REQUEST, "작성자와 요청자의 ID가 일치하지 않아 삭제하지 못했습니다"),
    NOT_EXIST_COMMENT_INFO_EXCEPTION(HttpStatus.BAD_REQUEST, "요청한 Comment를 찾을 수 없습니다")



    ;


    private final HttpStatus status;
    private final String message;

    ErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}