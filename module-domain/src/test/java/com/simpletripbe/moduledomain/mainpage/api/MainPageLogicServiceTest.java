package com.simpletripbe.moduledomain.mainpage.api;

import com.simpletripbe.moduledomain.mainpage.dto.MainPageListDTO;
import com.simpletripbe.moduledomain.mainpage.dto.OrderType;
import com.simpletripbe.moduledomain.mainpage.dto.dataApi.PermissionRequest;
import com.simpletripbe.moduledomain.mainpage.dto.dataApi.PermissionResponse;
import com.simpletripbe.moduledomain.mainpage.entity.MainPage;
import com.simpletripbe.moduledomain.mainpage.mapper.MainPageMapper;
import com.simpletripbe.moduledomain.mainpage.repository.MainPageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainPageLogicServiceTest {

    @Autowired
    MainPageRepository mainPageRepository;
    @Autowired
    MainPageLogicService mainPageLogicService;

    @Test
    @DisplayName("전체 리스트 조회 TEST")
    public void selectAll() {
        //given
        for(int i = 0; i < 10; i++) {
            MainPage content = MainPage.builder()
                    .image("image")
                    .continent("Asia")
                    .country("korea")
                    .createDate(LocalDateTime.now())
                    .build();
            mainPageRepository.save(content);
        }
        //when
        List<MainPageListDTO> result = mainPageLogicService.selectAll(OrderType.ALL);
        //then
        MainPageListDTO resultInstance = result.get(0);
        assertThat(result.size()).isEqualTo(10);
        assertThat(resultInstance).isInstanceOf(MainPageListDTO.class);
        assertThat(resultInstance).isNotInstanceOf(MainPage.class);
    }

    @Test
    @DisplayName("검색어 조회 TEST")
    public void selectBySearchWord() {
        //given
        for(int i = 0; i < 10; i++) {
            MainPage content = MainPage.builder()
                    .image("image")
                    .continent("Asia")
                    .country("korea")
                    .createDate(LocalDateTime.now())
                    .build();
            mainPageRepository.save(content);
        }
        String searchword = "korea";
        //when
        List<MainPageListDTO> result = mainPageLogicService.selectBySearchWord(searchword);
        //then
        MainPageListDTO resultInstance = result.get(0);
        assertThat(result.size()).isEqualTo(10);
        assertThat(resultInstance).isInstanceOf(MainPageListDTO.class);
        assertThat(resultInstance).isNotInstanceOf(MainPage.class);
    }

    @Test
    @DisplayName("외교부_국가·지역별 입국허가요건 데이터셋 TEST")
    public void selectEntryPermitRequirements() throws DataFormatException {
        //given
        PermissionRequest permissionRequest = new PermissionRequest();
        permissionRequest.setServiceKey("VU2wvwgzw2TjYrvH%2FCdTQFmI7vn8rh4yNog9eSBCRPeg473G8%2F%2BMcwGZQbHluVTOyOSr2c0MoGUdFwOPm9a6mg%3D%3D");
        permissionRequest.setReturnType("JSON");
        permissionRequest.setNumOfRows(10);
        permissionRequest.setPageNo(1);
        permissionRequest.setCountry_nm("%EA%B0%80%EB%82%98");
        permissionRequest.setCountry_iso_alp2("GH");
        //when
        PermissionResponse result = mainPageLogicService.selectEntryPermitRequirements(permissionRequest);
        //then
        assertThat(result.getResultMsg()).isEqualTo("정상");
        assertThat(result.getResultCode()).isEqualTo(0);
    }

}