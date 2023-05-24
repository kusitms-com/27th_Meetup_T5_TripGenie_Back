package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.moduledomain.mycarrier.dto.StorageDTO;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainStorageService {

    private final MyCarrierRepository myCarrierRepository;

    /**
     * 보관함 리스트 조회 서비스 로직
     */
    @Transactional
    public List<StorageDTO> selectAll(String email) {

        return myCarrierRepository.findStorageByEmail(email);

    }

}
