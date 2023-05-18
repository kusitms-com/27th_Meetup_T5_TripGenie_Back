package com.simpletripbe.moduledomain.mypage.dto;

import lombok.Data;

@Data
public class MyPageProfileListDTO {

    private String name;
    private String gender;
    private String nickname;
    private String email;
    private String birth;
    private String picture;
    private Integer cash;

}
