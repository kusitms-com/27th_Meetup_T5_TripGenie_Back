package com.simpletripbe.moduledomain.community.api;

import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.community.entity.Community;
import com.simpletripbe.moduledomain.community.mapper.CommunityMapper;
import com.simpletripbe.moduledomain.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainCommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;

    public List<InfoDTO> selectAll() {

        List<Community> entityList = communityRepository.findAll();

        List<InfoDTO> resultList = communityMapper.toDTO(entityList);

        return resultList;
    }

}
