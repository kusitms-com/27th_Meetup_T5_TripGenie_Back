package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.mapper.MyCarrierMapper;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

}
