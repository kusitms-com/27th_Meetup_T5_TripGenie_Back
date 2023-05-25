package com.simpletripbe.moduledomain.mycarrier.dto.CarrierEdit;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EditCarrierCountryResDTO {

    private Long id;
    private String email;
    private String country;

}
