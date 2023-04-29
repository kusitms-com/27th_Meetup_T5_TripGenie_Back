package com.simpletripbe.moduleapi.applications.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpReq {
    @NotBlank
    private String name;
    @NotBlank
    private String nickname;
    private String pictureUrl;
    @NotBlank
    private String gender;
    @NotBlank
    private String birth;
    @Email
    @NotBlank
    private String email;
}
