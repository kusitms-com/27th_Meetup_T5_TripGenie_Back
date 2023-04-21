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
    public List<String> selectAll() {
        return mainCarrierService
                .selectAll();
    }

    @Transactional(readOnly = true)
    public List<CarrierListDTO> selectDetailAll(String country) {
        return mainCarrierService
                .selectDetailAll(country);
    }

    @Transactional(readOnly = true)
    public void saveOne(CarrierListDTO carrierListDTO) {
        mainCarrierService.addCarrier(carrierListDTO);
    }

    @Transactional(readOnly = true)
    public void editOne(CarrierListDTO carrierListDTO) {
        mainCarrierService.editCarrier(carrierListDTO);
    }

    @Transactional(readOnly = true)
    public void deleteOne(Long id) {
        mainCarrierService.deleteCarrier(id);
    }

    @Transactional(readOnly = true)
    public void saveStamp(CarrierListDTO carrierListDTO) {
        mainCarrierService.addStamp(carrierListDTO);
    }

}
