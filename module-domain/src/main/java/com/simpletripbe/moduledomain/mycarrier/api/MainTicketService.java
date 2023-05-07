package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.moduledomain.mycarrier.dto.TicketRecordDTO;
import com.simpletripbe.moduledomain.mycarrier.mapper.MyCarrierMapper;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainTicketService {

    private final MyCarrierRepository myCarrierRepository;
    private final MyCarrierMapper myCarrierMapper;

    public String selectMyTicketRecord(String ticket) {

        return myCarrierRepository.selectMyTicketRecord(ticket);
    }

    public String insertMyStampRecord(TicketRecordDTO ticketRecordDTO) {

        myCarrierRepository.insertMyStampRecord(ticketRecordDTO);
        return "SUCCESS";
    }

    public String updateMyStampRecord(TicketRecordDTO ticketRecordDTO) {

        myCarrierRepository.updateMyStampRecord(ticketRecordDTO);
        return "SUCCESS";
    }

    public String deleteMyStampRecord(String country) {

        myCarrierRepository.deleteMyStampRecord(country);
        return "SUCCESS";
    }


}
