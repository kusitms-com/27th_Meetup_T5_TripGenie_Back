package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.mapper.MyCarrierMapper;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainCarrierService {

    private final MyCarrierRepository myCarrierRepository;
    private final MyCarrierMapper myCarrierMapper;

    public List<String> selectAll() {

        List<String> entityResult = myCarrierRepository.findAllByDbsts();

        return entityResult;
    }

    public List<CarrierListDTO> selectDetailAll(String country) {

        List<MyCarrier> entityResult = myCarrierRepository.findAllByCountry(country);
        List<CarrierListDTO> result = myCarrierMapper.toCarrierDto(entityResult);

        return result;
    }

    public void addCarrier(CarrierListDTO carrierListDTO) {

        MyCarrier myCarrier = myCarrierMapper.toEntity(carrierListDTO);

        myCarrierRepository.save(myCarrier);
    }

    public void editCarrier(CarrierListDTO carrierListDTO) {

        myCarrierRepository.updateCarrier(carrierListDTO);
    }

    public void deleteCarrier(Long id) {

        myCarrierRepository.deleteCarrier(id);
    }

    public void addStamp(CarrierListDTO carrierListDTO) {

        MyCarrier myCarrier = myCarrierMapper.toEntity(carrierListDTO);

        myCarrierRepository.save(myCarrier);
    }

    public void updateDetailInfo(CarrierListDTO carrierListDTO) {

        myCarrierRepository.updateCarrier(carrierListDTO);
    }

}
