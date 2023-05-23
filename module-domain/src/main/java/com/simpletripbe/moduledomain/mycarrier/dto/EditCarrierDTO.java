package com.simpletripbe.moduledomain.mycarrier.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EditCarrierDTO {

    private String email;
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
    private String name;

}
