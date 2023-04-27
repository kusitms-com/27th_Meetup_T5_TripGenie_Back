package com.simpletripbe.moduledomain.mypage.api;

import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.community.entity.Community;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import com.simpletripbe.moduledomain.mypage.entity.MyPage;
import com.simpletripbe.moduledomain.mypage.mapper.MyPageMapper;
import com.simpletripbe.moduledomain.mypage.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainMyPageService {

    private final MyPageMapper myPageMapper;
    private final MyPageRepository myPageRepository;

    public List<MyPageProfileListDTO> selectAll() {

        List<MyPage> entityList = myPageRepository.findAll();

        List<MyPageProfileListDTO> resultList = entityList.stream().map(entity -> myPageMapper.toDTO(entity)).collect(Collectors.toList());

        return resultList;
    }

}
