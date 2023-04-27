package com.simpletripbe.moduledomain.community.api;

import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.community.entity.Community;
import com.simpletripbe.moduledomain.community.mapper.CommunityMapper;
import com.simpletripbe.moduledomain.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainCommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;

    public List<InfoDTO> selectAll() {

        List<Community> entityList = communityRepository.findAll();

        List<InfoDTO> resultList = entityList.stream().map(entity -> communityMapper.toDTO(entity)).collect(Collectors.toList());

        return resultList;
    }

}
