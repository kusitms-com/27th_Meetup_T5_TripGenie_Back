package com.simpletripbe.modulecommon.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommonCode {
    // SUCCESS
    SUCCESS(200, 200, "성공"),
    OAUTH_CHECK_SUCCESS(200, 201, "Oauth 로그인 확인"),
    // FAIL
    FAIL(500, -1, "실패. 알 수 없는 오류"),
    INVALID_ELEMENTS(400, -2, "조건에 맞지 않는 요소(elements)가 있습니다"),

    //-1000: USER
    NOT_EXIST_ID(200,-1000, "존재하지 않는 이메일입니다."),
    WRONG_PASSWORD(200, -1001, "비밀번호가 일치하지 않습니다."),
    USER_ALREADY_EXIST(200, -1002, "해당 아이디가 이미 존재합니다."),
    NICKNAME_ALREADY_EXIST(200, -1003 , "중복된 닉네임입니다." ),
    INVALID_SOCIAL_LOGIN_TYPE(200, -1004, "알 수 없는 소셜 로그인 형식입니다."),
    OAUTH_LOGIN_FAILED(200, -1005, "Oauth에서 프로필 정보를 가져오는데 실패했습니다."),
    SIGNUP_REQUIRED(200, -1006, "최초 로그인입니다."),
    WRONG_SIGNUP(400, -1007, "올바르지 않은 회원가입입니다."),


    //-2000: MeetUp

    //-3000: Posts

    //-4000: Gateway

    //-5000: JWT
    // JWT
    EMPTY_JWT_TOKEN(400, -5000, "JWT 토큰이 없습니다."),
    INVALID_TOKEN(400, -5001, "유효하지 않은 토큰입니다.");



    private int status;
    private int code;
    private String message;

    CommonCode(int status, int code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
