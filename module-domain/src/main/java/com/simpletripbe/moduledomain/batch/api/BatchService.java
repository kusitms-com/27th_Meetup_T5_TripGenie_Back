package com.simpletripbe.moduledomain.batch.api;

import com.simpletripbe.moduledomain.batch.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepository batchRepository;

    public void saveStartAlarm(LocalDate endDate) {

        //TODO -- endDate로 조회하고, 리스트 뽑아온 후 저장하는 로직 구현 필요

    }

    public void saveMyBag(LocalDate endDate) {

        //TODO -- endDate로 조회하고, 리스트 뽑아온 후 저장하는 로직 구현 필요

    }

    public void saveEndAlarm(LocalDate endDate) {

        //TODO -- endDate로 조회하고, 리스트 뽑아온 후 저장하는 로직 구현 필요

    }

}
