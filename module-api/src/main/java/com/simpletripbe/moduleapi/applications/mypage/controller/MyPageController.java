package com.simpletripbe.moduleapi.applications.mypage.controller;

import com.simpletripbe.moduleapi.applications.mypage.service.MyPageService;
import com.simpletripbe.modulecommon.common.response.BaseResponseBody;
import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageDocumentListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
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
@RequestMapping(value = "/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/selectMyProfile")
    public ResponseEntity<BaseResponseBody<MyPageProfileListDTO>> selectMyProfile(
            @RequestParam String nickname
    ) {

        MyPageProfileListDTO profile = myPageService.selectMyProfile(nickname);

        return new ResponseEntity<BaseResponseBody<MyPageProfileListDTO>>(
                new BaseResponseBody<MyPageProfileListDTO>(
                        HttpStatus.OK.value(),
                        "성공",
                        profile
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/selectMyDocument")
    public ResponseEntity<BaseResponseBody<List<MyPageDocumentListDTO>>> selectMyDocument(
            @RequestParam String nickname
    ) {

        List<MyPageDocumentListDTO> list = myPageService.selectMyDocumentList(nickname);

        return new ResponseEntity<BaseResponseBody<List<MyPageDocumentListDTO>>>(
                new BaseResponseBody<List<MyPageDocumentListDTO>>(
                        HttpStatus.OK.value(),
                        "성공",
                        list
                ),
                HttpStatus.OK
        );
    }

}
