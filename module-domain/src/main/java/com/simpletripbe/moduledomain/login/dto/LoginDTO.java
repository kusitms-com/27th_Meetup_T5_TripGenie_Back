package com.simpletripbe.moduledomain.login.dto;

import com.simpletripbe.modulecommon.common.annotation.Email;
import com.simpletripbe.modulecommon.common.annotation.NotBlank;
import lombok.Getter;

@Getter
public class LoginDTO {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
}

