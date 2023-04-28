package com.simpletripbe.moduledomain.mypage.api;

import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.community.entity.Community;
import com.simpletripbe.moduledomain.mypage.dto.MyPageDocumentListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageStampListDTO;
import com.simpletripbe.moduledomain.mypage.entity.MyPage;
import com.simpletripbe.moduledomain.mypage.mapper.MyPageMapper;
import com.simpletripbe.moduledomain.mypage.repository.MyPageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainMyPageServiceTest {

    @Autowired
    MyPageRepository myPageRepository;
    @Autowired
    MainMyPageService mainMyPageService;

    @Test
    @DisplayName("내 프로필 조회 test")
    public void selectMyProfile() {
        //given
        MyPage content = MyPage.builder()
                .birth("20001215")
                .email("austinan@naver.com")
                .gender("M")
                .picture("picture")
                .nickname("hoohoo")
                .createDate(LocalDateTime.now())
                .updDate(LocalDateTime.now())
                .build();
        myPageRepository.save(content);
        //when
        MyPageProfileListDTO result = mainMyPageService.selectMyProfile("hoohoo");
        //then
        assertThat(result.getBirth()).isEqualTo("20001215");
        assertThat(result.getEmail()).isEqualTo("austinan@naver.com");
    }

    @Test
    @DisplayName("내 서류 조회 test")
    public void selectMyDocument() {
        //given
        for(int i = 0; i < 10; i++) {
            MyPage content = MyPage.builder()
                    .birth("20001215")
                    .email("austinan@naver.com")
                    .gender("M")
                    .picture("picture")
                    .nickname("hoohoo")
                    .createDate(LocalDateTime.now())
                    .updDate(LocalDateTime.now())
                    .build();
            myPageRepository.save(content);
        }
        //when
        List<MyPageDocumentListDTO> result = mainMyPageService.selectMyDocument("hoohoo");
        //then
        MyPageDocumentListDTO resultInstance = result.get(0);
        assertThat(result.size()).isEqualTo(10);
        assertThat(resultInstance).isInstanceOf(MyPageDocumentListDTO.class);
        assertThat(resultInstance).isNotInstanceOf(MyPage.class);
    }

    @Test
    @DisplayName("내 스탬프 조회 test")
    public void selectMyStamp() {
        //given
        for(int i = 0; i < 10; i++) {
            MyPage content = MyPage.builder()
                    .birth("20001215")
                    .email("austinan@naver.com")
                    .gender("M")
                    .picture("picture")
                    .nickname("hoohoo")
                    .createDate(LocalDateTime.now())
                    .updDate(LocalDateTime.now())
                    .build();
            myPageRepository.save(content);
        }
        //when
        List<MyPageStampListDTO> result = mainMyPageService.selectMyStamp("hoohoo");
        //then
        MyPageStampListDTO resultInstance = result.get(0);
        assertThat(result.size()).isEqualTo(10);
        assertThat(resultInstance).isInstanceOf(MyPageStampListDTO.class);
        assertThat(resultInstance).isNotInstanceOf(MyPage.class);
    }

}