package com.simpletripbe.moduleapi.applications.mycarrier.controller;

import com.simpletripbe.moduleapi.applications.mycarrier.service.MyCarrierService;
import com.simpletripbe.modulecommon.common.response.ApiResponse;
import com.simpletripbe.modulecommon.common.response.BaseResponseBody;
import com.simpletripbe.modulecommon.common.util.EmptyResponse;
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
    @Operation(summary = "캐리어 추가 api", description = "addCarrier")
    @PostMapping("addCarrier")
    public ApiResponse<EmptyResponse> addCarrier(
            @RequestBody CarrierListDTO carrierListDTO
    ) {

        myCarrierService.saveOne(carrierListDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 캐리어 수정
     */
    @Operation(summary = "캐리어 수정 api", description = "editCarrier")
    @PutMapping("editCarrier")
    public ApiResponse<EmptyResponse> editCarrier(
            @RequestBody CarrierListDTO carrierListDTO
    ) {

        myCarrierService.editOne(carrierListDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 캐리어 삭제 - soft delete 로 dbsts 만 S 로 변경
     */
    @Operation(summary = "캐리어 삭제 api", description = "deleteCarrier")
    @PutMapping("deleteCarrier")
    public ApiResponse<EmptyResponse> deleteCarrier(
            @RequestBody Long userId
    ) {

        myCarrierService.deleteOne(userId);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 체크표시 선택 시 스탬프 생성
     */
    @Operation(summary = "스탬프 생성 api", description = "addStamp")
    @PostMapping("addCarrier")
    public ApiResponse<EmptyResponse> addStamp(
            @RequestBody CarrierListDTO carrierListDTO
    ) {

        myCarrierService.saveStamp(carrierListDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

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
