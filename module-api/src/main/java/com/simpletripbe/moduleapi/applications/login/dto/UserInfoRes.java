package com.simpletripbe.moduleapi.applications.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserInfoRes {

    String email;
    String userName;
    String pictureUrl;
    String accessToken;
    String refreshToken;

}
