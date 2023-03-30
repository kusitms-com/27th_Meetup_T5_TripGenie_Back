package com.simpletripbe.modulecommon.common.response;

import java.io.Serializable;

public class BaseResponseBody<T> implements Serializable {
    private Integer resultCode;
    private String resultMessage;
    private T result;

    public BaseResponseBody() {
    }

    public BaseResponseBody(Integer resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public BaseResponseBody(Integer resultCode, String resultMessage, T result) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.result = result;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
