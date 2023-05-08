package com.simpletripbe.moduledomain.batch.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TicketListDTO {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

}
