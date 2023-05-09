package com.simpletripbe.moduledomain.batch.dto;

import com.simpletripbe.moduledomain.mycarrier.entity.TicketType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MyBagTicketDTO {

    private TicketType type;
    private String ticketUrl;
    private String imageUrl;
    private String title;
    private String sequence;
    private LocalDate endDate;

    public MyBagTicketDTO(TicketType type, String ticketUrl, String imageUrl, String title, String sequence, LocalDate endDate) {
        this.type = type;
        this.ticketUrl = ticketUrl;
        this.imageUrl = imageUrl;
        this.title = title;
        this.sequence = sequence;
        this.endDate = endDate;
    }
}