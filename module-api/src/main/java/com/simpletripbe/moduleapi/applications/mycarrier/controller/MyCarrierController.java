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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResponse<List<CarrierListDTO>> selectAll (
            @PageableDefault final Pageable pageable
    ) {

        final Page<CarrierListDTO> responses
                = myCarrierService.selectAll(pageable);

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
     * 상세페이지 - 목록 조회
     */

    /**
     * 상세페이지 - 이미지, 파일, 링크 저장
     */

}
