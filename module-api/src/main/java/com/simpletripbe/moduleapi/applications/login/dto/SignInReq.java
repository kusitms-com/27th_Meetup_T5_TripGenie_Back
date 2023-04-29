package com.simpletripbe.moduleapi.applications.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignInReq {
    @Email
    private String email;
}
