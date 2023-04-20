package com.simpletripbe.moduledomain.mycarrier.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class CarrierListDTO {

    private String country;
    private String image;
    private String file;
    private String link;

}
