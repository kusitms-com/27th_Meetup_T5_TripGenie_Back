package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.batch.dto.MyBagSaveDTO;
import com.simpletripbe.moduledomain.batch.dto.MyBagTicketDTO;
import com.simpletripbe.moduledomain.batch.dto.TicketListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierSelectDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketTypeDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.StorageDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;

import java.util.List;

public interface MyCarrierRepositoryCustom {

    List<CarrierSelectDTO> findAllByEmail(String email);

    List<TicketListDTO> selectCarrierList();

    List<MyBagTicketDTO> selectTicketList();

    void updateToMyBag(MyBagSaveDTO dto);

    List<Ticket> findTicketByEmail(String email);

    List<StorageDTO> findStorageByEmail(String email);
}
