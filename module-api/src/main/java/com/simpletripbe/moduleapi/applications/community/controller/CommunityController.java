package com.simpletripbe.moduleapi.applications.community.controller;

import com.simpletripbe.moduleapi.applications.community.service.CommunityService;
import com.simpletripbe.modulecommon.common.response.BaseResponseBody;
import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping(value = "/selectAll")
    public ResponseEntity<BaseResponseBody<List<InfoDTO>>> selectAllList() {

        List<InfoDTO> list = communityService.selectAllList();

        return new ResponseEntity<BaseResponseBody<List<InfoDTO>>>(
                new BaseResponseBody<List<InfoDTO>>(
                        HttpStatus.OK.value(),
                        "성공",
                        list
                ),
                HttpStatus.OK
        );
    }

}
