package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyCarrierRepositoryCustom {

    Page<CarrierListDTO> findAllbyPage(Pageable pageable);

}
