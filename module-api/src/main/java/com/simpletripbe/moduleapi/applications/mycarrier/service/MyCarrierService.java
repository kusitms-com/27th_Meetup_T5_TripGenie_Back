package com.simpletripbe.moduleapi.applications.mycarrier.service;

import com.simpletripbe.moduledomain.mycarrier.api.MainCarrierService;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyCarrierService {

    private final MainCarrierService mainCarrierService;

    @Transactional(readOnly = true)
    public List<Country> selectAll() {
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

    @Transactional(readOnly = true)
    public void saveInfo(CarrierListDTO carrierListDTO) {
        mainCarrierService.updateDetailInfo(carrierListDTO);
    }

}
