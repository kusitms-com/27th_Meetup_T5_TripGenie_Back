package com.simpletripbe.moduledomain.login.dto;

import com.simpletripbe.modulecommon.common.annotation.Email;
import com.simpletripbe.modulecommon.common.annotation.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailDTO implements Serializable {
    @NotBlank
    private String name;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
    private String pictureUrl;
    private Date createdDt = null;
    private String description;
    @Email
    @NotBlank
    private String email;
    private String github;
    private String blog;
    private List<String> tags;

}

