package com.simpletripbe.moduledomain.mycarrier.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EditCarrierDTO {

    private String email;
    private EditCarrierType type;
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
    private String name;

}
