package com.simpletripbe.moduledomain.mypage.api;

import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.community.entity.Community;
import com.simpletripbe.moduledomain.mypage.dto.MyPageDocumentListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageStampListDTO;
import com.simpletripbe.moduledomain.mypage.dto.StampRecordDTO;
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

    public MyPageProfileListDTO selectMyProfile(String nickname) {

        MyPage entity = myPageRepository.findProfileByNickname(nickname);

        MyPageProfileListDTO result = myPageMapper.toProfileDTO(entity);

        return result;
    }

    public List<MyPageDocumentListDTO> selectMyDocument(String nickname) {

        List<MyPage> entityList = myPageRepository.findDocumentByNickname(nickname);

        List<MyPageDocumentListDTO> resultList = entityList.stream().map(entity -> myPageMapper.toDocumentDTO(entity)).collect(Collectors.toList());

        return resultList;
    }

    public List<MyPageStampListDTO> selectMyStamp(String nickname) {

        List<MyPage> entityList = myPageRepository.findStampByNickname(nickname);

        List<MyPageStampListDTO> resultList = entityList.stream().map(entity -> myPageMapper.toStampDTO(entity)).collect(Collectors.toList());

        return resultList;
    }

    public String selectMyStampRecord(String country) {

        return myPageRepository.selectMyStampRecord(country);
    }

    public String insertMyStampRecord(StampRecordDTO stampRecordDTO) {

        myPageRepository.insertMyStampRecord(stampRecordDTO);
        return "SUCCESS";
    }

    public String updateMyStampRecord(StampRecordDTO stampRecordDTO) {

        myPageRepository.updateMyStampRecord(stampRecordDTO);
        return "SUCCESS";
    }

    public String deleteMyStampRecord(String country) {

        myPageRepository.deleteMyStampRecord(country);
        return "SUCCESS";
    }

}
