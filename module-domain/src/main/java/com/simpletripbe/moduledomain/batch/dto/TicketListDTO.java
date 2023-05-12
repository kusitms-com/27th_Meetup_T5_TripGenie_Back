package com.simpletripbe.moduledomain.batch.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TicketListDTO {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    // 생성자 추가
    public TicketListDTO(LocalDate startDate, LocalDate endDate, String name) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
    }

}
