package com.simpletripbe.moduledomain.mycarrier.dto;

import com.simpletripbe.moduledomain.mycarrier.entity.CarrierType;
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
    private String deleteYn;
    private CarrierType type;

}
