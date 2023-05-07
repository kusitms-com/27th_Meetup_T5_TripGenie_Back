package com.simpletripbe.modulecommon.common.response;

import lombok.Data;

import java.util.Map;

@Data
public class CommonResponse<T> {
    private Boolean isSuccess;
    private int code;
    private String message;
    private Map<String, T> attribute;

    public CommonResponse(CommonCode commonCode, Map<String, T> attribute) {
        this.code = commonCode.getCode();
        this.message = commonCode.getMessage();
        this.attribute = attribute;
    }

    public CommonResponse(CommonCode commonCode, String message, Map<String, T> attribute) {
        this.code = commonCode.getCode();
        this.message = message;
        this.attribute = attribute;
    }

    public CommonResponse(Boolean isSuccess, CommonCode commonCode, Map<String, T> attribute) {
        this.isSuccess = isSuccess;
        this.code = commonCode.getCode();
        this.message = commonCode.getMessage();
        this.attribute = attribute;
    }

    public CommonResponse(Boolean isSuccess, CommonCode commonCode) {
        this.isSuccess = isSuccess;
        this.code = commonCode.getCode();
        this.message = commonCode.getMessage();
    }

}
