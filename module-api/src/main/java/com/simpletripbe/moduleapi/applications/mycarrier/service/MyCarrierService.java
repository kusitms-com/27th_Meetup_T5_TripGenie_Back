package com.simpletripbe.moduleapi.applications.mycarrier.service;

import com.simpletripbe.moduledomain.mycarrier.api.MainCarrierService;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyCarrierService {

    private final MainCarrierService mainCarrierService;

    @Transactional(readOnly = true)
    public List<CarrierListDTO> selectAll() {
        return mainCarrierService
                .selectAll();
    }

}
