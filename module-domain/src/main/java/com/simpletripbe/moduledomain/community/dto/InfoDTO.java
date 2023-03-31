package com.simpletripbe.moduledomain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoDTO {

    private Long id;
    private String content;
    private LocalDateTime createDate;

}
