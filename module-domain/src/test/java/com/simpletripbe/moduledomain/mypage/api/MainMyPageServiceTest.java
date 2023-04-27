package com.simpletripbe.moduledomain.mypage.api;

import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.community.entity.Community;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import com.simpletripbe.moduledomain.mypage.entity.MyPage;
import com.simpletripbe.moduledomain.mypage.mapper.MyPageMapper;
import com.simpletripbe.moduledomain.mypage.repository.MyPageRepository;
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
    public void Test() {
        //given
        for(int i = 0; i < 10; i++) {
            MyPage content = MyPage.builder()
                    .birth("20001215")
                    .email("austinan@naver.com")
                    .gender("M")
                    .picture("picture")
                    .createDate(LocalDateTime.now())
                    .updDate(LocalDateTime.now())
                    .build();
            myPageRepository.save(content);
        }
        //when
        List<MyPageProfileListDTO> result = mainMyPageService.selectAll();
        //then
        MyPageProfileListDTO resultInstance = result.get(0);
        assertThat(result.size()).isEqualTo(10);
        assertThat(resultInstance).isInstanceOf(MyPageProfileListDTO.class);
        assertThat(resultInstance).isNotInstanceOf(MyPage.class);
    }

}