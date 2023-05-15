package com.simpletripbe.moduledomain.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SocialOAuthDTO {

    String email;
    String name;
    String picture;

}