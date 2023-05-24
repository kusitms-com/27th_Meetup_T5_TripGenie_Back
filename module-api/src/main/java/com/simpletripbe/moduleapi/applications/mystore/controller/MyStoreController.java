package com.simpletripbe.moduleapi.applications.mystore.controller;

import com.simpletripbe.moduleapi.applications.mystore.service.MyStoreService;
import com.simpletripbe.modulecommon.common.annotation.AuthUser;
import com.simpletripbe.modulecommon.common.response.ApiResponse;
import com.simpletripbe.moduledomain.mystore.dto.PointReqDTO;
import com.simpletripbe.moduledomain.mystore.dto.UpdatePointDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "MyStoreController", description = "스토어 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/myStore")
public class MyStoreController {

    private final MyStoreService myStoreService;

    /**
     * 포인트 조회
     */
    @Operation(summary = "포인트 조회 api", description = "selectPoint")
    @GetMapping("selectPoint")
    public ApiResponse<Integer> selectPoint(@AuthUser String email) {

        final Integer responses
                = myStoreService.selectPoint(email);

        return ApiResponse.success(responses);

    }

    /**
     * 포인트 차감 (db에서가 아닌 단순 차감 기능)
     */
    @Operation(summary = "포인트 차감 api", description = "updatePoint")
    @PutMapping("updatePoint")
    public ApiResponse<Integer> updatePoint(
            @AuthUser String email,
            @RequestBody PointReqDTO pointReqDTO
            ) {

        UpdatePointDTO pointDTO = UpdatePointDTO.builder()
                                            .point(pointReqDTO.getPoint())
                                            .email(email)
                                            .build();

        final Integer responses
                = myStoreService.updatePoint(pointDTO);

        return ApiResponse.success(responses);

    }

}
