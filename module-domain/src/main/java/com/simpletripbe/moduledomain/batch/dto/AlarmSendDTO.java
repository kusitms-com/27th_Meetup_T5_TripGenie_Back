package com.simpletripbe.moduledomain.batch.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AlarmSendDTO {

    private String message;
    private LocalDate startDate;
    private LocalDate endDate;

}
