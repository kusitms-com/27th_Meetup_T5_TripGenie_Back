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

    @Transactional
    public Integer updatePoint(UpdatePointDTO pointDTO) {

        Integer basicPoint = selectPoint(pointDTO.getEmail());

        Integer updatePoint = basicPoint - pointDTO.getPoint();

        pointDTO.setPoint(updatePoint);
        saveNewPoint(pointDTO);

        return updatePoint;
    }

    @Transactional
    public void saveNewPoint(UpdatePointDTO pointDTO) {

        mainStoreService.updatePoint(pointDTO);

    }

}
