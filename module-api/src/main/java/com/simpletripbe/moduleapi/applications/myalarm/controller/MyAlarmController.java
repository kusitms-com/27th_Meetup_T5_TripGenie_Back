package com.simpletripbe.moduleapi.applications.myalarm.controller;

import com.simpletripbe.moduleapi.applications.myalarm.service.MyAlarmService;
import com.simpletripbe.modulecommon.common.annotation.AuthUser;
import com.simpletripbe.modulecommon.common.response.ApiResponse;
import com.simpletripbe.moduledomain.myalarm.dto.AlarmInfoDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierSelectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "MyAlarmController", description = "나의 알람 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/myAlarm")
public class MyAlarmController {

    private final MyAlarmService myAlarmService;

    @Operation(summary = "컈리어 전체 목록 조회 api", description = "selectAll")
    @GetMapping("selectAllAlarms")
    public ApiResponse<List<AlarmInfoDTO>> selectAll(
            @AuthUser String email) {

        final List<AlarmInfoDTO> responses
                = myAlarmService.selectAllAlarms(email);

        return ApiResponse.success(responses);

    }

}
