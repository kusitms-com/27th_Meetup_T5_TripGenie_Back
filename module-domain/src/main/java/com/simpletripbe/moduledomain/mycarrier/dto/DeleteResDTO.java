package com.simpletripbe.moduledomain.mycarrier.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteResDTO {

    private String email;
    private String name;
    private Long id;

}
