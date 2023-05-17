package com.simpletripbe.moduleapi.applications.mystore.service;

import com.simpletripbe.moduledomain.mystore.api.MainStoreService;
import com.simpletripbe.moduledomain.mystore.dto.UpdatePointDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyStoreService {

    private final MainStoreService mainStoreService;

    @Transactional(readOnly = true)
    public Integer selectPoint(String email) {
        return mainStoreService
                .selectPoint(email);
    }

    @Transactional(readOnly = true)
    public Integer updatePoint(UpdatePointDTO pointDTO) {

        Integer updatePoint = pointDTO.getPrice() - pointDTO.getPoint();

        return updatePoint;
    }

}
