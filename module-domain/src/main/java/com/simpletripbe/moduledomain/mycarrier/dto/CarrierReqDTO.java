package com.simpletripbe.moduledomain.mycarrier.dto;

import com.simpletripbe.moduledomain.mycarrier.entity.Country;
import lombok.Data;

@Data
public class CarrierReqDTO {

    private String country;
    private String name;
    private String startDate;
    private String endDate;

}
