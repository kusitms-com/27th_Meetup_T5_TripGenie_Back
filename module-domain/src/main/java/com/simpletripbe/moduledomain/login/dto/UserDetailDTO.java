package com.simpletripbe.moduledomain.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailDTO implements Serializable {
    private String id;
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
    @NotBlank
    private Date date;

}

