package com.simpletripbe.modulecommon.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private Integer resultCode;
    private String message;
}
