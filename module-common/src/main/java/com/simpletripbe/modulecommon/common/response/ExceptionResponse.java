package com.simpletripbe.modulecommon.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private int status;
    private int code; // common code
    private String message;

    public ExceptionResponse(CommonCode commonCode, String message) {
        this.status = commonCode.getStatus();
        this.code = commonCode.getCode();
        this.message = message;
    }
}
