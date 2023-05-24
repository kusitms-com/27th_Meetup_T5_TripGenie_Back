package com.simpletripbe.moduleapi.applications.mycarrier.controller;

import com.simpletripbe.moduleapi.applications.mycarrier.service.TicketRecordService;
import com.simpletripbe.modulecommon.common.annotation.AuthUser;
import com.simpletripbe.modulecommon.common.response.ApiResponse;
import com.simpletripbe.modulecommon.common.util.EmptyResponse;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "TicketRecordController", description = "티켓 기록 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/ticket/memo")
public class TicketRecordController {

    private final TicketRecordService ticketRecordService;

    /**
     * 티켓 메모 추가 컨트롤러
     */
    @Operation(summary = "나의 티켓 기록 작성 api", description = "insertTicketMemo")
    @PostMapping("")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<TicketMemoRes> insertTicketMemo(
            @AuthUser String email,
            @RequestPart(value = "dto") TicketMemoDTO ticketMemoDTO,
            @RequestPart(value = "file") MultipartFile multipartFile
    ) throws FileUploadException {

        return ApiResponse.success(ticketRecordService.insertTicketMemo(email, ticketMemoDTO, multipartFile));

    }

    /**
     * 티켓 메모 조회 컨트롤러
     */
    @Operation(summary = "나의 티켓 기록 조회 api", description = "selectTicketMemo")
    @GetMapping("{carrierId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<TicketMemoRes> selectTicketMemo(
            @AuthUser String email,
            @PathVariable Long carrierId,
            @RequestParam("id") Long ticketId
    ) {

        return ApiResponse.success(ticketRecordService.selectTicketMemo(email, carrierId, ticketId));

    }

    /**
     * 티켓 메모 수정 컨트롤러
     */
    @Operation(summary = "나의 티켓 기록 수정 api", description = "updateTicketMemo")
    @PostMapping("update")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<TicketMemoRes> updateTicketMemo(
            @AuthUser String email,
            @RequestPart(value = "dto") TicketMemoDTO ticketMemoDTO,
            @RequestPart(value = "file") MultipartFile multipartFile
    ) throws FileUploadException {

        return ApiResponse.success(ticketRecordService.updateTicketMemo(email, ticketMemoDTO, multipartFile));

    }

    /**
     * 티켓 메모 삭제 컨트롤러
     */
    @Operation(summary = "나의 티켓 기록 삭제 api", description = "deleteTicketMemo")
    @DeleteMapping("{carrierId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<EmptyResponse> deleteTicketMemo(
            @AuthUser String email,
            @PathVariable Long carrierId,
            @RequestParam("id") Long ticketId
    ) {

        ticketRecordService.deleteTicketMemo(email, carrierId, ticketId);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 티켓 메모 존재 여부 조회 컨트롤러
     */
    @Operation(summary = "티켓 기록 존재 여부 조회 api", description = "checkExist")
    @GetMapping("/checkExist/{carrierId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<EmptyResponse> checkExist(
            @AuthUser String email,
            @PathVariable Long carrierId,
            @RequestParam("id") Long ticketId
    ) {

        ticketRecordService.checkExist(email, carrierId, ticketId);

        return ApiResponse.success(EmptyResponse.of());
    }
}
