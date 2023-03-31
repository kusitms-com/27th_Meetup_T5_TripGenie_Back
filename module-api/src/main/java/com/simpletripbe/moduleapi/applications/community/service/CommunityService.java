package com.simpletripbe.moduleapi.applications.community.service;

import com.simpletripbe.moduledomain.community.api.MainCommunityService;
import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final MainCommunityService mainCommunityService;

    public List<InfoDTO> selectAllList() {

        return mainCommunityService.selectAll();

    }

}
