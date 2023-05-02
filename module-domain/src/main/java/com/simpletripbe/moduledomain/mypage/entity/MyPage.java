package com.simpletripbe.moduledomain.mypage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;
    private String name;
    private String gender;
    private String nickname;
    private String email;
    private String birth;
    private String picture;
    private String record;
    private String country;
    private String content;
    private String dbsts;
    private LocalDateTime createDate;
    private LocalDateTime updDate;

}
