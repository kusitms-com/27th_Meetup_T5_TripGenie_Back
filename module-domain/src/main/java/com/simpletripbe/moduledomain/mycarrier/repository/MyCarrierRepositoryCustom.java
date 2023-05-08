package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.entity.CarrierCountry;
import com.simpletripbe.moduledomain.mycarrier.entity.Country;
import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;

import java.util.List;

public interface MyCarrierRepositoryCustom {

    List<String> findAllByEmail(String email);


    List<Ticket> findTicketByEmail(String email);

}
