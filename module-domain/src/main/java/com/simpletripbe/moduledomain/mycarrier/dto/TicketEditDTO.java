package com.simpletripbe.moduledomain.mycarrier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketEditDTO {

    private Long carrierId;
    private Long ticketId;
    private Integer sequence;
    private String title;

}