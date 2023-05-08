package com.simpletripbe.moduleapi.applications.mycarrier.controller;

import com.simpletripbe.moduleapi.applications.mycarrier.service.TicketRecordService;
import com.simpletripbe.modulecommon.common.response.BaseResponseBody;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketRecordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TicketRecordController", description = "티켓 기록 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/ticket")
public class TicketRecordController {

    private final TicketRecordService ticketRecordService;

    @Operation(summary = "나의 티켓 기록 조회 api", description = "selectMyTicketRecord")
    @GetMapping("/selectMyTicketRecord")
    public ResponseEntity<BaseResponseBody<String>> selectMyTicketRecord(
            @RequestParam String ticket
    ) {

        String record = ticketRecordService.selectRecord(ticket);

        return new ResponseEntity<BaseResponseBody<String>>(
                new BaseResponseBody<String>(
                        HttpStatus.OK.value(),
                        "성공",
                        record
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "나의 티켓 기록 작성 api", description = "insertMyTicketRecord")
    @PostMapping("/insertMyTicketRecord")
    public ResponseEntity<BaseResponseBody<String>> insertMyTicketRecord(
            @RequestBody TicketRecordDTO ticketRecordDTO
    ) {

        String record = ticketRecordService.insertRecord(ticketRecordDTO);

        return new ResponseEntity<BaseResponseBody<String>>(
                new BaseResponseBody<String>(
                        HttpStatus.OK.value(),
                        "성공",
                        record
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "나의 티켓 기록 수정 api", description = "editMyTicketRecord")
    @PutMapping("/editMyTicketRecord")
    public ResponseEntity<BaseResponseBody<String>> editMyTicketRecord(
            @RequestBody TicketRecordDTO ticketRecordDTO
    ) {

        String record = ticketRecordService.updateRecord(ticketRecordDTO);

        return new ResponseEntity<BaseResponseBody<String>>(
                new BaseResponseBody<String>(
                        HttpStatus.OK.value(),
                        "성공",
                        record
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "나의 티켓 기록 삭제 api", description = "updateTicketStatus")
    @PutMapping("/updateTicketStatus")
    public ResponseEntity<BaseResponseBody<String>> updateTicketStatus(
            @RequestParam String country
    ) {

        String record = ticketRecordService.deleteRecord(country);

        return new ResponseEntity<BaseResponseBody<String>>(
                new BaseResponseBody<String>(
                        HttpStatus.OK.value(),
                        "성공",
                        record
                ),
                HttpStatus.OK
        );
    }


}
