package com.simpletripbe.moduledomain.batch.api;

import com.simpletripbe.moduledomain.batch.dto.MyBagSaveDTO;
import com.simpletripbe.moduledomain.batch.repository.BatchRepository;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final MyCarrierRepository myCarrierRepository;

    public void saveStartAlarm(LocalDate endDate) {

        //TODO -- endDate로 조회하고, 리스트 뽑아온 후 저장하는 로직 구현 필요

    }

    public void saveMyBag(MyBagSaveDTO dto) {

        //TODO -- endDate로 조회하고, 리스트 뽑아온 후 저장하는 로직 구현 필요


    }

    public void saveEndAlarm(LocalDate endDate) {

        //TODO -- endDate로 조회하고, 리스트 뽑아온 후 저장하는 로직 구현 필요

    }

}
