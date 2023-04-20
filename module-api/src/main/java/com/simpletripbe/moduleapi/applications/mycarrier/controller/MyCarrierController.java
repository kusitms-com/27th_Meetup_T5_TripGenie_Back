package com.simpletripbe.moduleapi.applications.mycarrier.controller;

import com.simpletripbe.moduleapi.applications.mycarrier.service.MyCarrierService;
import com.simpletripbe.modulecommon.common.response.ApiResponse;
import com.simpletripbe.modulecommon.common.response.BaseResponseBody;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MyCarrierController", description = "나의 캐리어 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/myCarrier")
public class MyCarrierController {

    private final MyCarrierService myCarrierService;

    /**
     * 캐리어 목록 조회
     */
    @Operation(summary = "컈리어 전체 목록 조회 api", description = "selectAll")
    @GetMapping("selectAll")
    public ApiResponse<List<String>> selectAll () {

        final List<String> responses
                = myCarrierService.selectAll();

        return ApiResponse.success(responses);

    }

    /**
     * 캐리어 추가
     */

    /**
     * 캐리어 수정
     */

    /**
     * 캐리어 삭제
     */

    /**
     * 체크표시 선택 시 스탬프 생성
     */

    /**
     * 상세페이지 - 해당 국가의 전체 목록 조회
     */
    @Operation(summary = "해당 국가의 전체 목록 조회 api", description = "selectDetailAll")
    @GetMapping("selectDetailAll")
    public ApiResponse<List<CarrierListDTO>> selectDetailAll(
            @RequestParam String country
    ) {

        final List<CarrierListDTO> responses
                = myCarrierService.selectDetailAll(country);

        return ApiResponse.success(responses);

    }

    /**
     * 상세페이지 - 이미지, 파일, 링크 저장
     */

}
