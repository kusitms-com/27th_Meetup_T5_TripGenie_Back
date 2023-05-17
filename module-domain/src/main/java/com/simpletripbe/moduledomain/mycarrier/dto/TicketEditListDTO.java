package com.simpletripbe.moduledomain.mycarrier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketEditListDTO {

    private Long carrierId;
    private List<TicketEditDTO> ticketEditDTOList;

}
