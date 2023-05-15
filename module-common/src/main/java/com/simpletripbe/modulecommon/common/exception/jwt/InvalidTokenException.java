package com.simpletripbe.modulecommon.common.exception.jwt;

import com.simpletripbe.modulecommon.common.response.CommonCode;

public class InvalidTokenException extends RuntimeException{

    private CommonCode commonCode = CommonCode.INVALID_TOKEN;

    public InvalidTokenException(String message) {
        super(message);
    }

    public CommonCode getCommonCode() {
        return commonCode;
    }
}
