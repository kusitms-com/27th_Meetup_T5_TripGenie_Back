package com.simpletripbe.moduledomain.mycarrier.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class CarrierListDTO {

    private String country;
    private String name;
    private String email;
    private LocalDate startDate;
    private LocalDate endDate;

}
