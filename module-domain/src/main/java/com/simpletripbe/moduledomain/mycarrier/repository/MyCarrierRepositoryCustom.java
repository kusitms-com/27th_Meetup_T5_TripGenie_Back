package com.simpletripbe.moduledomain.mycarrier.repository;

import com.querydsl.core.Tuple;
import com.simpletripbe.moduledomain.batch.dto.MyBagTicketDTO;
import com.simpletripbe.moduledomain.batch.dto.TicketListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketTypeDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;

import java.util.List;

public interface MyCarrierRepositoryCustom {

    List<String> findAllByEmail(String email);

    List<TicketListDTO> selectCarrierList();

    List<MyBagTicketDTO> selectTicketList();

    List<Ticket> findTicketByEmail(String email);

}
