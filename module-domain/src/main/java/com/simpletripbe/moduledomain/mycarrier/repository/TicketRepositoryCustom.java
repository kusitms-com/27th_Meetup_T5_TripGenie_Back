package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.dto.TicketEditDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;

import java.util.List;

public interface TicketRepositoryCustom {

    List<Ticket> findAllByCarrierIdOrderBySequenceAsc(Long carrierId);

    void updateTicketSequence(List<TicketEditDTO> ticketEditDTOS);
}