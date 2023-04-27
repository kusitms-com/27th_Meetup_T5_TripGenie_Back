package com.simpletripbe.moduledomain.mainpage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "mainpage")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mainpage_id")
    private Long id;
    private String continent;
    private String country;
    private String image;
    private LocalDateTime createDate;

}
