package com.simpletripbe.moduledomain.myalarm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlarmInfoDTO {

    private Long id;
    private String message;
    private String createdDate;

    public AlarmInfoDTO(Long id, String message, String createdDate) {
        this.id = id;
        this.message = message;
        this.createdDate = createdDate;
    }

}
