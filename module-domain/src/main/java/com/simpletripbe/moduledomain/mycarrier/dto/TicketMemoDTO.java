package com.simpletripbe.moduledomain.mycarrier.dto;

import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketMemoDTO {

    private Long carrierId;
    private Long ticketId;
    private String imageUrl;
    private String content;

    private Ticket ticket;

    public void setMapper(Ticket ticket) {
        this.ticket = ticket;
    }
}

