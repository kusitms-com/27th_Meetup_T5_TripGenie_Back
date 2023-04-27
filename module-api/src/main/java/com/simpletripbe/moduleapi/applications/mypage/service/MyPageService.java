package com.simpletripbe.moduleapi.applications.mypage.service;


import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MyPageService {

    private final MainMyPageService mainMyPageService;

    public List<InfoDTO> selectAllList() {

        return mainMyPageService.selectAll();

    }

}
