package com.simpletripbe.moduleapi.applications.mainpage.service;

import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.mainpage.api.MainPageLogicService;
import com.simpletripbe.moduledomain.mainpage.dto.MainPageListDTO;
import com.simpletripbe.moduledomain.mainpage.dto.OrderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final MainPageLogicService mainPageLogicService;

    public List<MainPageListDTO> selectAllList(OrderType orderType) {

        return mainPageLogicService.selectAll(orderType);

    }

}
