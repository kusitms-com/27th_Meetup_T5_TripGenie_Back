package com.simpletripbe.moduledomain.myalarm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlarmInfoDTO {

    private Long id;
    private String message;

    public AlarmInfoDTO(Long id, String message) {
        this.id = id;
        this.message = message;
    }

}
