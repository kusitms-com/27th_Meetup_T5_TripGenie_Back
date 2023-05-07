package com.simpletripbe.moduledomain.batch.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MyBagSaveDTO {

    private String country;
    private LocalDate endDate;

}
