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

        List<MainPage> entityList = null;

        if (orderType.equals(OrderType.ALL)) {
            entityList = mainPageRepository.findAll();
        } else if (orderType.equals(OrderType.CONTINENT)) {
            entityList = mainPageRepository.findBySort(orderType);
        } else if (orderType.equals(OrderType.SPELL)) {
            //TODO -- 가나다 순 정렬 어떻게 할지 고민
            entityList = mainPageRepository.findBySort(orderType);
        }

        List<MainPageListDTO> resultList = mainPageMapper.toDTO(entityList);

        return resultList;
    }

    public List<MainPageListDTO> selectBySearchWord(String searchWord) {

        List<MainPage> entityList = mainPageRepository.findBySearchWord(searchWord);

        List<MainPageListDTO> resultList = mainPageMapper.toDTO(entityList);

        return resultList;
    }

}
