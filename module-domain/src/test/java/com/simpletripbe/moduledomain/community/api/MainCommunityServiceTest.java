package com.simpletripbe.moduledomain.community.api;

import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.community.entity.Community;
import com.simpletripbe.moduledomain.community.mapper.CommunityMapper;
import com.simpletripbe.moduledomain.community.repository.CommunityRepository;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainCommunityServiceTest {

    @Autowired
    MainCommunityService communityService;
    @Autowired
    CommunityRepository communityRepository;
    @Test
    public void Test() {
        //given
        for(int i = 0; i < 10; i++) {
            Community content = Community.builder()
                    .content("content" + i)
                    .createDate(LocalDateTime.now())
                    .build();
            communityRepository.save(content);
        }
        //when
        List<InfoDTO> result = communityService.selectAll();
        //then
        InfoDTO resultInstance = result.get(0);
        assertThat(result.size()).isEqualTo(10);
        assertThat(resultInstance).isInstanceOf(InfoDTO.class);
        assertThat(resultInstance).isNotInstanceOf(Community.class);
    }
}