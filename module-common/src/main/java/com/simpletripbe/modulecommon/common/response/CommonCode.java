package com.simpletripbe.modulecommon.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommonCode {
    // SUCCESS
    SUCCESS(200, 200, "성공"),
    // FAIL
    FAIL(500, -1, "실패. 알 수 없는 오류"),
    INVALID_ELEMENTS(400, -2, "조건에 맞지 않는 요소(elements)가 있습니다"),

    //-1000: USER
    OAUTH_CHECK_SUCCESS(200, -1000, "Oauth 로그인 확인"),
    FIRST_TIME_LOGIN(200, -1001, "최초 로그인입니다."),
    SIGNUP_REQUIRED(200, -1002, "회원가입이 필요합니다."),

    USER_ALREADY_EXIST(400, -1003, "해당 아이디가 이미 존재합니다."),
    EMPTY_NICKNAME(400, -1004, "닉네임이 존재하지 않습니다."),
    WRONG_SIGNUP(400, -1005, "올바르지 않은 회원가입입니다."),
    INVALID_GOOGLE_ACCESS_TOKEN(400, -1006, "유효하지 않은 구글 액세스 토큰입니다."),


    //-2000: MeetUp

    //-3000: Posts

    //-4000: Gateway

    //-5000: JWT
    EMPTY_JWT_TOKEN(400, -5000, "JWT 토큰이 없습니다."),
    INVALID_TOKEN(400, -5001, "유효하지 않은 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(400, -5002, "인증 정보가 만료되었습니다."),
    INVALID_REFRESH_TOKEN(400, -5003, "잘못된 리프레시 토큰입니다.");



    private int status;
    private int code;
    private String message;

    CommonCode(int status, int code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
