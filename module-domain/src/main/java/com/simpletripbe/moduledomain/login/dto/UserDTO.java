package com.simpletripbe.moduledomain.login.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@ToString(exclude = "password")
public class UserDTO {

    @Id
    private Long id;
    private String name;
    private String nickname;
    @JsonIgnore
    private String password;
    private String pictureUrl;
    private Date createdDt;
    private String description;
    private String email;
    private String github;
    private String blog;
    private List<String> tags;

    //Oauth를 위해 구성한 추가 필드
    private String provider;

}
