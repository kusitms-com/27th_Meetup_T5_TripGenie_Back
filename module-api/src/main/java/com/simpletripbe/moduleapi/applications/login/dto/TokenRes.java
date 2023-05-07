package com.simpletripbe.moduleapi.applications.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
public class TokenRes {
    private String accessToken;
    private String refreshToken;
}
