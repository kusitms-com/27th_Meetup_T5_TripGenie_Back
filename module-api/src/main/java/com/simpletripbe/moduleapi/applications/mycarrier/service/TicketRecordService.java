package com.simpletripbe.moduleapi.applications.mycarrier.service;

import com.simpletripbe.moduledomain.mycarrier.api.MainTicketService;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketRecordService {

    private final MainTicketService mainTicketService;

    public void insertTicketMemo(String email, TicketMemoDTO ticketMemoDTO) {
        mainTicketService.insertTicketMemo(email, ticketMemoDTO);
    }

    public void selectTicketMemo(String email, Long carrierId, Long ticketId) {
        mainTicketService.selectTicketMemo(email, carrierId, ticketId);
    }

    public void updateTicketMemo(String email, TicketMemoDTO ticketMemoDTO) {
        mainTicketService.updateTicketMemo(email, ticketMemoDTO);
    }

    public void deleteTicketMemo(String email, Long carrierId, Long ticketId) {
        mainTicketService.deleteTicketMemo(email, carrierId, ticketId);
    }
}
