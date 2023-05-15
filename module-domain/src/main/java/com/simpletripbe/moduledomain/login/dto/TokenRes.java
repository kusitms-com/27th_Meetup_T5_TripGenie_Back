package com.simpletripbe.moduledomain.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class TokenRes {
    private String accessToken;
    private String refreshToken;

    public TokenRes(String accessToken) {
        this.accessToken = accessToken;
    }

}
