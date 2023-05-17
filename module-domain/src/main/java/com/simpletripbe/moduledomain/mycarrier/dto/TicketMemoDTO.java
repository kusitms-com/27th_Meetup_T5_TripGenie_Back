package com.simpletripbe.moduledomain.mycarrier.dto;

import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketMemoDTO {

    private Long carrierId;
    private Long ticketId;
    private String imageUrl;

    @Size(message = "500글자를 초과하였습니다.", max = 500)
    private String content;

    private Ticket ticket;

    public void setMapper(Ticket ticket) {
        this.ticket = ticket;
    }
}

