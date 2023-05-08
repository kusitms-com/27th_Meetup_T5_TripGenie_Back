package com.simpletripbe.moduledomain.batch.dto;

import com.simpletripbe.moduledomain.mycarrier.entity.CarrierType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MyBagSaveDTO {

    private CarrierType type;
    private LocalDateTime updDate;

}
