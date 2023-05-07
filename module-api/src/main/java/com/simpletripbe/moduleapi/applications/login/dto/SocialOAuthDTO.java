package com.simpletripbe.moduleapi.applications.login.dto;

import com.simpletripbe.moduledomain.login.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SocialOAuthDTO {
    String email;
    String userName;
    String pictureUrl;

    public SocialOAuthDTO(UserDTO user) {
        this.email = user.getEmail();
        this.userName = user.getName();
        this.pictureUrl = user.getPictureUrl();
    }
}