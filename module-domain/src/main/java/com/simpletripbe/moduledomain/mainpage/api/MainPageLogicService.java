package com.simpletripbe.moduledomain.mainpage.api;

import com.simpletripbe.moduledomain.mainpage.dto.MainPageListDTO;
import com.simpletripbe.moduledomain.mainpage.dto.OrderType;
import com.simpletripbe.moduledomain.mainpage.entity.MainPage;
import com.simpletripbe.moduledomain.mainpage.mapper.MainPageMapper;
import com.simpletripbe.moduledomain.mainpage.repository.MainPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageLogicService {

    private final MainPageRepository mainPageRepository;
    private final MainPageMapper mainPageMapper;

    public List<MainPageListDTO> selectAll(OrderType orderType) {

        List<MainPage> entityList = mainPageRepository.findAll();

        List<MainPageListDTO> resultList = mainPageMapper.toDTO(entityList);

        return resultList;
    }

}
