package com.simpletripbe.moduleapi.applications.mycarrier.service;

import com.simpletripbe.moduledomain.mycarrier.api.MainTicketService;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketRecordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketRecordService {

    private final MainTicketService mainTicketService;

    public String selectRecord(String ticket) {

        return mainTicketService.selectMyTicketRecord(ticket);

    }

    public String insertRecord(TicketRecordDTO ticketRecordDTO) {

        return mainTicketService.insertMyStampRecord(ticketRecordDTO);

    }

    public String updateRecord(TicketRecordDTO ticketRecordDTO) {

        return mainTicketService.updateMyStampRecord(ticketRecordDTO);

    }

    public String deleteRecord(String country) {

        return mainTicketService.deleteMyStampRecord(country);

    }


}
