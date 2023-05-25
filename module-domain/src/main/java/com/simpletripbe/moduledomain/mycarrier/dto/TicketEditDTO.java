package com.simpletripbe.moduledomain.mycarrier.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketEditDTO {

    private Long carrierId;
    private Long ticketId;
    private Integer sequence;
    private String title;

}