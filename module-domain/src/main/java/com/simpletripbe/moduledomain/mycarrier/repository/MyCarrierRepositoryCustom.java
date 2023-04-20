package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MyCarrierRepositoryCustom {

    List<MyCarrier> findAllByDbsts();

}
