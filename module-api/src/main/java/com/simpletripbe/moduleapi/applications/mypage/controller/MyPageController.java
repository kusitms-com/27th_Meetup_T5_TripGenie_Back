package com.simpletripbe.moduleapi.applications.mypage.controller;

import com.simpletripbe.moduleapi.applications.mypage.service.MyPageService;
import com.simpletripbe.modulecommon.common.annotation.AuthUser;
import com.simpletripbe.modulecommon.common.response.BaseResponseBody;
import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageDocumentListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageStampListDTO;
import com.simpletripbe.moduledomain.mypage.dto.StampRecordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "MyPageController", description = "마이 페이지 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "나의 프로필 조회 api", description = "selectMyProfile")
    @GetMapping("/selectMyProfile")
    public ResponseEntity<BaseResponseBody<MyPageProfileListDTO>> selectMyProfile(
            @RequestParam String nickname
    ) {

        MyPageProfileListDTO profile = myPageService.selectMyProfile(nickname);

        return new ResponseEntity<BaseResponseBody<MyPageProfileListDTO>>(
                new BaseResponseBody<MyPageProfileListDTO>(
                        HttpStatus.OK.value(),
                        "성공",
                        profile
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "나의 닉네임 수정 api", description = "updateMyNickname")
    @PutMapping("/updateMyNickname")
    public ResponseEntity<BaseResponseBody> updateMyNickname(
            @AuthUser String email,
            @RequestBody String nickname
    ) {

        MyPageProfileListDTO listDTO = new MyPageProfileListDTO();
        listDTO.setNickname(nickname);
        listDTO.setEmail(email);

        myPageService.updateMyNickname(listDTO);

        return new ResponseEntity<BaseResponseBody>(
                new BaseResponseBody(
                        HttpStatus.OK.value(),
                        "성공"
                ),
                HttpStatus.OK
        );

    }

    @Operation(summary = "나의 프로필 사진 수정 api", description = "updateMyProfileImage")
    @PutMapping("/updateMyProfileImage")
    public ResponseEntity<BaseResponseBody> updateMyProfileImage(
            @AuthUser String email,
            @RequestPart(value = "image") MultipartFile image
    ) throws FileUploadException {

        MyPageProfileListDTO listDTO = new MyPageProfileListDTO();
        listDTO.setEmail(email);
        listDTO.setImage(image);

        myPageService.updateMyProfileImage(listDTO);

        return new ResponseEntity<BaseResponseBody>(
                new BaseResponseBody(
                        HttpStatus.OK.value(),
                        "성공"
                ),
                HttpStatus.OK
        );

    }

    @Operation(summary = "나의 서류 조회 api", description = "selectMyDocument")
    @GetMapping("/selectMyDocument")
    public ResponseEntity<BaseResponseBody<List<MyPageDocumentListDTO>>> selectMyDocument(
            @RequestParam String nickname
    ) {

        List<MyPageDocumentListDTO> list = myPageService.selectMyDocumentList(nickname);

        return new ResponseEntity<BaseResponseBody<List<MyPageDocumentListDTO>>>(
                new BaseResponseBody<List<MyPageDocumentListDTO>>(
                        HttpStatus.OK.value(),
                        "성공",
                        list
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "나의 스탬프 조회 api", description = "selectMyStamp")
    @GetMapping("/selectMyStamp")
    public ResponseEntity<BaseResponseBody<List<MyPageStampListDTO>>> selectMyStamp(
            @RequestParam String nickname
    ) {

        List<MyPageStampListDTO> list = myPageService.selectMyStampList(nickname);

        return new ResponseEntity<BaseResponseBody<List<MyPageStampListDTO>>>(
                new BaseResponseBody<List<MyPageStampListDTO>>(
                        HttpStatus.OK.value(),
                        "성공",
                        list
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "나의 스탬프 기록 조회 api", description = "selectMyStampRecord")
    @GetMapping("/selectMyStampRecord")
    public ResponseEntity<BaseResponseBody<String>> selectMyStampRecord(
            @RequestParam String country
    ) {

        String record = myPageService.selectRecord(country);

        return new ResponseEntity<BaseResponseBody<String>>(
                new BaseResponseBody<String>(
                        HttpStatus.OK.value(),
                        "성공",
                        record
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "나의 스탬프 기록 작성 api", description = "insertMyStampRecord")
    @PostMapping("/insertMyStampRecord")
    public ResponseEntity<BaseResponseBody<String>> insertMyStampRecord(
            @RequestBody StampRecordDTO stampRecordDTO
    ) {

        String record = myPageService.insertRecord(stampRecordDTO);

        return new ResponseEntity<BaseResponseBody<String>>(
                new BaseResponseBody<String>(
                        HttpStatus.OK.value(),
                        "성공",
                        record
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "나의 스탬프 기록 수정 api", description = "editMyStampRecord")
    @PutMapping("/editMyStampRecord")
    public ResponseEntity<BaseResponseBody<String>> editMyStampRecord(
            @RequestBody StampRecordDTO stampRecordDTO
    ) {

        String record = myPageService.updateRecord(stampRecordDTO);

        return new ResponseEntity<BaseResponseBody<String>>(
                new BaseResponseBody<String>(
                        HttpStatus.OK.value(),
                        "성공",
                        record
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "나의 스탬프 기록 삭제 api", description = "updateRecordStatus")
    @PutMapping("/updateRecordStatus")
    public ResponseEntity<BaseResponseBody<String>> updateRecordStatus(
            @RequestParam String country
    ) {

        String record = myPageService.deleteRecord(country);

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
