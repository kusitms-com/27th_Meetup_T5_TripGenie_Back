package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainCarrierService {

    private final MyCarrierRepository myCarrierRepository;

    public Page<CarrierListDTO> selectAll(
            final Pageable pageable
    ) {
        return myCarrierRepository.findAllbyPage(pageable);
    }

}
