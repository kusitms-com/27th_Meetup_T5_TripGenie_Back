package com.simpletripbe.moduleapi.applications.mycarrier.controller;

import com.simpletripbe.moduleapi.applications.mycarrier.service.StorageService;
import com.simpletripbe.modulecommon.common.annotation.AuthUser;
import com.simpletripbe.modulecommon.common.response.ApiResponse;
import com.simpletripbe.moduledomain.mycarrier.dto.StorageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "StorageController", description = "보관함 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/storage")
public class StorageController {

    private final StorageService storageService;

    @Operation(summary = "보관함 전체 목록 조회 api", description = "selectAll")
    @GetMapping("")
    public ApiResponse<List<StorageDTO>> selectAll(@AuthUser String email) {

        return ApiResponse.success(storageService.selectAll(email));

    }

}
