package com.simpletripbe.moduledomain.myalarm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlarmInfoDTO {

    private Long id;
    private String message;
    private String date;

    public AlarmInfoDTO(Long id, String message, String date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }

}
