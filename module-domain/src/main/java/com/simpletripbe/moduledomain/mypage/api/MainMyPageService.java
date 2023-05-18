package com.simpletripbe.moduledomain.mypage.api;

import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.community.entity.Community;
import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.mycarrier.api.AwsS3Service;
import com.simpletripbe.moduledomain.mypage.dto.MyPageDocumentListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageStampListDTO;
import com.simpletripbe.moduledomain.mypage.dto.StampRecordDTO;
import com.simpletripbe.moduledomain.mypage.entity.MyPage;
import com.simpletripbe.moduledomain.mypage.mapper.MyPageMapper;
import com.simpletripbe.moduledomain.mypage.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainMyPageService {

    @Value("${cloud.aws.s3.url}")
    private String s3Url;
    private final MyPageMapper myPageMapper;
    private final MyPageRepository myPageRepository;
    private final AwsS3Service awsS3Service;

    public MyPageProfileListDTO selectMyProfile(String nickname) {

        User entity = myPageRepository.findProfileByNickname(nickname);

        MyPageProfileListDTO result = myPageMapper.toProfileDTO(entity);

        return result;
    }

    public void updateMyNickname(MyPageProfileListDTO listDTO) {

        myPageRepository.updateMyNickname(listDTO);
    }

    public void updateMyProfileImage(MyPageProfileListDTO listDTO) throws FileUploadException {

        String profileImage = myPageRepository.selectMyProfileImageByNickname(listDTO.getNickname());

        if (profileImage == "EMPTY") {
            throw new CustomException(CommonCode.EMPTY_PROFILE_IMAGE);
        } else {

            // 기존 이미지 삭제
            awsS3Service.deleteFile(profileImage.replace(s3Url, ""));
            //새 이미지 업로드
            awsS3Service.uploadFile(listDTO.getImage());
        }

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
