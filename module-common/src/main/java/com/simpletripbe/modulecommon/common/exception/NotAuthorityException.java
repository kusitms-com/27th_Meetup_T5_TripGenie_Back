package com.simpletripbe.modulecommon.common.exception;

public class NotAuthorityException extends RuntimeException {

    public NotAuthorityException() {
        super("잘못된 인증 정보 입니다.");
    }
}
