package com.simpletripbe.moduleapi.applications.mypage.controller;

import com.simpletripbe.moduleapi.applications.mypage.service.MyPageService;
import com.simpletripbe.modulecommon.common.response.BaseResponseBody;
import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/selectAll")
    public ResponseEntity<BaseResponseBody<List<MyPageProfileListDTO>>> selectAllList() {

        List<MyPageProfileListDTO> list = myPageService.selectAllList();

        return new ResponseEntity<BaseResponseBody<List<MyPageProfileListDTO>>>(
                new BaseResponseBody<List<MyPageProfileListDTO>>(
                        HttpStatus.OK.value(),
                        "성공",
                        list
                ),
                HttpStatus.OK
        );
    }

}
