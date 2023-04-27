package com.simpletripbe.moduledomain.community.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class InfoDTO {

    private Long id;
    private String content;
    private LocalDateTime createDate;

}
