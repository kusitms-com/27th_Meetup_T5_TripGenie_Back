package com.simpletripbe.moduledomain.mystore.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePointDTO {

    private Integer point;
    private String email;

}
