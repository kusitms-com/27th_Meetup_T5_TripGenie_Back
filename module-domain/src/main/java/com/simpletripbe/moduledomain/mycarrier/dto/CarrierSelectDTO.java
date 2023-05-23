package com.simpletripbe.moduledomain.mycarrier.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarrierSelectDTO {
    private Long id;
    private String name;

    public CarrierSelectDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
