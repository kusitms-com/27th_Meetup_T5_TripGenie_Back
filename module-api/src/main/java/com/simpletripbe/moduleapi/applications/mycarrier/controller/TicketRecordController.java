package com.simpletripbe.moduleapi.applications.mycarrier.controller;

import com.simpletripbe.moduleapi.applications.mycarrier.service.TicketRecordService;
import com.simpletripbe.modulecommon.common.annotation.AuthUser;
import com.simpletripbe.modulecommon.common.response.ApiResponse;
import com.simpletripbe.modulecommon.common.util.EmptyResponse;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TicketRecordController", description = "티켓 기록 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/ticket/memo")
public class TicketRecordController {

    private final TicketRecordService ticketRecordService;

    @Operation(summary = "나의 티켓 기록 작성 api", description = "insertTicketMemo")
    @PostMapping("")
    public ApiResponse<EmptyResponse> insertTicketMemo(
            @AuthUser String email,
            @RequestBody TicketMemoDTO ticketMemoDTO
    ) {

        ticketRecordService.insertTicketMemo(email, ticketMemoDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    @Operation(summary = "나의 티켓 기록 조회 api", description = "selectTicketMemo")
    @GetMapping("{carrierId}")
    public ApiResponse<EmptyResponse> selectTicketMemo(
            @AuthUser String email,
            @PathVariable Long carrierId,
            @RequestParam("id") Long ticketId
    ) {

        ticketRecordService.selectTicketMemo(email, carrierId, ticketId);

        return ApiResponse.success(EmptyResponse.of());
    }

    @Operation(summary = "나의 티켓 기록 수정 api", description = "updateTicketMemo")
    @PutMapping("")
    public ApiResponse<EmptyResponse> updateTicketMemo(
            @AuthUser String email,
            @RequestBody TicketMemoDTO ticketMemoDTO
    ) {

        ticketRecordService.updateTicketMemo(email, ticketMemoDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    @Operation(summary = "나의 티켓 기록 삭제 api", description = "deleteTicketMemo")
    @DeleteMapping("")
    public ApiResponse<EmptyResponse> deleteTicketMemo(
            @AuthUser String email,
            @PathVariable Long carrierId,
            @RequestParam("id") Long ticketId
    ) {

        ticketRecordService.deleteTicketMemo(email, carrierId, ticketId);

        return ApiResponse.success(EmptyResponse.of());
    }


}
