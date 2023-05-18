package com.simpletripbe.moduledomain.mypage.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MyPageProfileListDTO {

    private String name;
    private String gender;
    private String nickname;
    private String email;
    private MultipartFile image;
    private String birth;
    private String picture;
    private Integer cash;

}
