package com.simpletripbe.moduledomain.login.dto;

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

    public UserInfoRes(SocialOAuthDTO socialOAuthDTO, String accessToken, String refreshToken) {
        this.email = socialOAuthDTO.getEmail();
        this.userName = socialOAuthDTO.getName();
        this.pictureUrl = socialOAuthDTO.getPicture();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public UserInfoRes(SocialOAuthDTO socialOAuthDTO, String accessToken) {
        this.email = socialOAuthDTO.getEmail();
        this.userName = socialOAuthDTO.getName();
        this.pictureUrl = socialOAuthDTO.getPicture();
        this.accessToken = accessToken;
    }
}
