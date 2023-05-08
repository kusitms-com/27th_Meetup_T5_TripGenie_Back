package com.simpletripbe.moduledomain.mycarrier.dto;

import com.simpletripbe.moduledomain.mycarrier.entity.TicketType;
import lombok.Data;

@Data
public class TicketTypeDTO {

    private TicketType type;
    private String ticketUrl;
    private String imageUrl;
    private String title;
    private String sequence;

}
