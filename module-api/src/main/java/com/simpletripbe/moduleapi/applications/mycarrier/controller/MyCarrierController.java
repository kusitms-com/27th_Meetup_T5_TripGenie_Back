package com.simpletripbe.moduleapi.applications.mycarrier.controller;

import com.simpletripbe.moduleapi.applications.mycarrier.service.MyCarrierService;
import com.simpletripbe.modulecommon.common.annotation.AuthUser;
import com.simpletripbe.modulecommon.common.response.ApiResponse;
import com.simpletripbe.modulecommon.common.util.EmptyResponse;
import com.simpletripbe.moduledomain.mycarrier.dto.*;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierEdit.*;
import com.simpletripbe.moduledomain.mycarrier.entity.CarrierType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Tag(name = "MyCarrierController", description = "나의 캐리어 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/myCarrier")
public class MyCarrierController {

    private final MyCarrierService myCarrierService;

    /**
     * 캐리어 목록 조회
     */
    @Operation(summary = "컈리어 전체 목록 조회 api", description = "selectAll")
    @GetMapping("selectAll")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<List<CarrierSelectDTO>> selectAll(@AuthUser String email) {

        final List<CarrierSelectDTO> responses
                = myCarrierService.selectAll(email);

        return ApiResponse.success(responses);

    }

    /**
     * 캐리어 정보 조회 컨트롤러
     */
    @Operation(summary = "캐리어 정보 조회 api", description = "getInfo")
    @GetMapping("getInfo")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<CarrierInfoRes> getInfo(
            @AuthUser String email,
            @RequestParam("id") Long carrierId
    ) {

        return ApiResponse.success(myCarrierService.getInfo(email, carrierId));

    }

    /**
     * 캐리어 추가
     */
    @Operation(summary = "캐리어 추가 api", description = "addCarrier")
    @PostMapping("addCarrier")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<EmptyResponse> addCarrier(
            @AuthUser String email,
            @RequestBody CarrierReqDTO carrierReqDTO
    ) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDate = LocalDate.parse(carrierReqDTO.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(carrierReqDTO.getEndDate(), formatter);

        CarrierListDTO carrierListDTO = new CarrierListDTO(
                carrierReqDTO.getCountry(),
                carrierReqDTO.getName(),
                email,
                startDate,
                endDate,
                "N",
                CarrierType.CARRIER
        );

        myCarrierService.saveOne(carrierListDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 캐리어 수정
     */
    @Operation(summary = "캐리어 이름 수정 api", description = "editCarrierName")
    @PutMapping("editCarrierName")
    public ApiResponse<EmptyResponse> editCarrierName(
            @AuthUser String email,
            @RequestBody EditCarrierNameReqDTO editCarrierNameReqDTO
    ) {

        EditCarrierNameResDTO carrierDTO = EditCarrierNameResDTO.builder()
                .name(editCarrierNameReqDTO.getName())
                .email(email)
                .id(editCarrierNameReqDTO.getId())
                .build();

        myCarrierService.editName(carrierDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    @Operation(summary = "캐리어 여행 기간 수정 api", description = "editCarrierPeriod")
    @PutMapping("editCarrierPeriod")
    public ApiResponse<EmptyResponse> editCarrierPeriod(
            @AuthUser String email,
            @RequestBody EditCarrierPeriodReqDTO editCarrierPeriodReqDTO
            ) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDate = LocalDate.parse(editCarrierPeriodReqDTO.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(editCarrierPeriodReqDTO.getEndDate(), formatter);

        EditCarrierPeriodResDTO carrierDTO = EditCarrierPeriodResDTO.builder()
                .email(email)
                .id(editCarrierPeriodReqDTO.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        myCarrierService.editPeriod(carrierDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    @Operation(summary = "캐리어 여행지 수정 api", description = "editCarrierCountry")
    @PutMapping("editCarrierCountry")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<EmptyResponse> editCarrierCountry(
            @AuthUser String email,
            @RequestBody EditCarrierCountryReqDTO editCarrierCountryReqDTO
            ) {

        EditCarrierCountryResDTO carrierDTO = EditCarrierCountryResDTO.builder()
                .id(editCarrierCountryReqDTO.getId())
                .country(editCarrierCountryReqDTO.getCountry())
                .email(email)
                .build();

        myCarrierService.editCountry(carrierDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 캐리어 삭제 - soft delete 로 deleteYn 만 Y 로 변경
     */
    @Operation(summary = "캐리어 삭제 api", description = "deleteCarrier")
    @PutMapping("deleteCarrier")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<EmptyResponse> deleteCarrier(
            @AuthUser String email,
            @RequestBody DeleteCarrierReqDTO deleteCarrierReqDTO
            ) {

        DeleteResDTO deleteResDTO = DeleteResDTO.builder()
                .name(deleteCarrierReqDTO.getName())
                .email(email)
                .build();

        myCarrierService.deleteOne(deleteResDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 체크표시 선택 시 스탬프 생성
     */
    @Operation(summary = "스탬프 생성 api", description = "addStamp")
    @PostMapping("addStamp")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<EmptyResponse> addStamp(
            @RequestBody CarrierListDTO carrierListDTO
    ) {

        myCarrierService.saveStamp(carrierListDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 상세페이지 - 티켓 리스트 조회 컨트롤러
     */
    @Operation(summary = "티켓 목록 조회 api", description = "selectTicketAll")
    @GetMapping("selectTicketAll")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<List<TicketDTO>> selectTicketAll(
            @AuthUser String email,
            @RequestParam("id") Long carrierId
    ) {

        return ApiResponse.success(myCarrierService.selectTicketAll(email, carrierId));

    }

    /**
     * 상세 페이지 - 티켓 링크 저장 컨트롤러
     */
    @Operation(summary = "티켓 url 추가 api", description = "addTicketInfo")
    @PostMapping("addTicket/url")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<List<TicketDTO>> addTicketUrl(
            @AuthUser String email,
            @RequestBody TicketUrlDTO ticketUrlDTO
    ) {

        return ApiResponse.success(myCarrierService.saveUrl(email, ticketUrlDTO));

    }

    /**
     * 상세 페이지 - 티켓 이미지, 파일 저장 컨트롤러
     */
    @Operation(summary = "티켓 파일 추가 api", description = "addTicketFile")
    @PostMapping("addTicket/file")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<List<TicketDTO>> addTicketFile(
            @AuthUser String email,
            @RequestPart(value = "dto") TicketUrlDTO ticketUrlDTO,
            @RequestPart(value = "file") MultipartFile multipartFile
    ) throws FileUploadException {

        return ApiResponse.success(myCarrierService.saveFile(email, ticketUrlDTO, multipartFile));

    }

    /**
     * 편집 - 티켓 순서 변경 컨트롤러
     */
    @Operation(summary = "티켓 순서 변경 api", description = "updateTicketOrder")
    @PutMapping("updateTicket/order")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<EmptyResponse> updateTicketOrder(
            @AuthUser String email,
            @RequestBody TicketEditListDTO ticketEditListDTO
    ) {

        myCarrierService.updateTicketOrder(email, ticketEditListDTO);

        return ApiResponse.success(EmptyResponse.of());

    }

    /**
     * 편집 - 티켓 이름 변경 컨트롤러
     */
    @Operation(summary = "티켓 이름 변경 api", description = "updateTicketTitle")
    @PutMapping("updateTicket/title")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<EmptyResponse> updateTicketTitle(
            @AuthUser String email,
            @RequestBody TicketEditDTO ticketEditDTO
    ) {

        myCarrierService.updateTicketTitle(email, ticketEditDTO);

        return ApiResponse.success(EmptyResponse.of());

    }

    /**
     * 티켓 삭제 컨트롤러
     */
    @Operation(summary = "티켓 삭제 api", description = "deleteTicket")
    @DeleteMapping("delete/{carrierId}/ticket")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<EmptyResponse> deleteTicket(
            @AuthUser String email,
            @PathVariable Long carrierId,
            @RequestParam("id") Long ticketId
    ) {

        myCarrierService.deleteTicket(email, carrierId, ticketId);

        return ApiResponse.success(EmptyResponse.of());
    }
}
