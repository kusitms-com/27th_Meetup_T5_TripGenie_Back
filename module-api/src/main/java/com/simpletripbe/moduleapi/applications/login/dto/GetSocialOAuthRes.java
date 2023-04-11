package com.simpletripbe.moduleapi.applications.login.dto;

import com.simpletripbe.moduledomain.community.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSocialOAuthRes {
    String email;
    String userName;
    String pictureUrl;

    public GetSocialOAuthRes(UserDTO user) {
        this.email = user.getEmail();
        this.userName = user.getName();
        this.pictureUrl = user.getPictureUrl();
    }
}