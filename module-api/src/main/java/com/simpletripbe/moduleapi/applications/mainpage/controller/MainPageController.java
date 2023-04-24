package com.simpletripbe.moduleapi.applications.mainpage.controller;

import com.simpletripbe.moduleapi.applications.mainpage.service.MainPageService;
import com.simpletripbe.modulecommon.common.response.BaseResponseBody;
import com.simpletripbe.moduledomain.mainpage.dto.MainPageListDTO;
import com.simpletripbe.moduledomain.mainpage.dto.OrderType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/main")
public class MainPageController {

    private final MainPageService mainPageService;

    /**
     * 전체 목록 조회 + 정렬 api
     */
    @GetMapping("selectSort")
    public ResponseEntity<BaseResponseBody<List<MainPageListDTO>>> selectAllBySort(
            @RequestParam OrderType orderType) {

        List<MainPageListDTO> list = mainPageService.selectAllList(orderType);

        return new ResponseEntity<BaseResponseBody<List<MainPageListDTO>>>(
                new BaseResponseBody<List<MainPageListDTO>>(
                        HttpStatus.OK.value(),
                        "성공",
                        list
                ),
                HttpStatus.OK
        );
    }

    /**
     * 검색 기능 api
     */
    @GetMapping("searchWord")
    public ResponseEntity<BaseResponseBody<List<MainPageListDTO>>> searchWord(
            @RequestParam String search
    ) {

        List<MainPageListDTO> list = mainPageService.selectOne(search);

        return new ResponseEntity<BaseResponseBody<List<MainPageListDTO>>>(
                new BaseResponseBody<List<MainPageListDTO>>(
                        HttpStatus.OK.value(),
                        "성공",
                        list
                ),
                HttpStatus.OK
        );
    }

}
