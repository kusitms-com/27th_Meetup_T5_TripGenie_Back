package com.simpletripbe.moduledomain.mycarrier.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.TicketType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketUrlDTO {

    private Long id;
    private TicketType type;
    private String url;

    private MyCarrier myCarrier;
    private String title;
    private Integer sequence;

    public void setMapper(MyCarrier myCarrier, String title, Integer sequence) {
        this.myCarrier = myCarrier;
        this.title = title;
        this.sequence = sequence;
    }

    public void setMapper(MyCarrier myCarrier, String title, String url, Integer sequence) {
        this.myCarrier = myCarrier;
        this.title = title;
        this.url = url;
        this.sequence = sequence;
    }
}
