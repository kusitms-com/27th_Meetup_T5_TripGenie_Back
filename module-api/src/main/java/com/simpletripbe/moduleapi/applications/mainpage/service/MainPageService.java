package com.simpletripbe.moduleapi.applications.mainpage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simpletripbe.moduledomain.mainpage.api.MainPageLogicService;
import com.simpletripbe.moduledomain.mainpage.dto.MainPageListDTO;
import com.simpletripbe.moduledomain.mainpage.dto.OrderType;
import com.simpletripbe.moduledomain.mainpage.dto.dataApi.PermissionRequest;
import com.simpletripbe.moduledomain.mainpage.dto.dataApi.PermissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final MainPageLogicService mainPageLogicService;

    public List<MainPageListDTO> selectAllList(OrderType orderType) {

        return mainPageLogicService.selectAll(orderType);

    }

    public List<MainPageListDTO> selectOne(String searchWord) {

        return mainPageLogicService.selectBySearchWord(searchWord);

    }

    public PermissionResponse selectDatas(PermissionRequest permissionRequest) throws DataFormatException {
        return mainPageLogicService.selectEntryPermitRequirements(permissionRequest);
    }

}
