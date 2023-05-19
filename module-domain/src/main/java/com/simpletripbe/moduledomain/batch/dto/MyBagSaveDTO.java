package com.simpletripbe.moduledomain.batch.dto;

import com.simpletripbe.moduledomain.mycarrier.entity.CarrierType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyBagSaveDTO {
    private Long ticketId;

    private CarrierType type;
    private LocalDateTime updDate;

}
