package com.simpletripbe.moduledomain.batch.api;

import com.simpletripbe.moduledomain.batch.repository.BatchRepository;
import com.simpletripbe.moduledomain.community.entity.Community;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepository batchRepository;

    public void save(LocalDate endDate) {

        //TODO -- endDate로 조회하고, 리스트 뽑아온 후 저장하는 로직 구현 필요

    }

}
