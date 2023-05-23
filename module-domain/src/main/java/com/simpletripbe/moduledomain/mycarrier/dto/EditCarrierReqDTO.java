package com.simpletripbe.moduledomain.mycarrier.dto;

import lombok.Data;

@Data
public class EditCarrierReqDTO {

    private String country;
    private String name;
    private String startDate;
    private String endDate;

}
