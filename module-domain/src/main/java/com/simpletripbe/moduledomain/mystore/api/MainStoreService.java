package com.simpletripbe.moduledomain.mystore.api;

import com.simpletripbe.moduledomain.mystore.dto.UpdatePointDTO;
import com.simpletripbe.moduledomain.mystore.mapper.MyStoreMapper;
import com.simpletripbe.moduledomain.mystore.repository.MyStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainStoreService {

    private final MyStoreMapper myStoreMapper;
    private final MyStoreRepository myStoreRepository;

    public Integer selectPoint(String email) {

        Integer entityResult = myStoreRepository.findPointByEmail(email);

        return entityResult;
    }

    public void updatePoint(UpdatePointDTO pointDTO) {

        myStoreRepository.updatePointByEmail(pointDTO);
    }

}
