package com.simpletripbe.moduleapi.applications.mystore.controller;

import com.simpletripbe.moduleapi.applications.login.jwt.JwtFilter;
import com.simpletripbe.moduleapi.applications.login.jwt.JwtTokenProvider;
import com.simpletripbe.moduleapi.applications.mystore.service.MyStoreService;
import com.simpletripbe.modulecommon.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "MyStoreController", description = "스토어 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/myStore")
public class MyStoreController {

    private final MyStoreService myStoreService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 포인트 조회
     */
    @Operation(summary = "컈리어 전체 목록 조회 api", description = "selectAll")
    @GetMapping("selectAll")
    public ApiResponse<Integer> selectPoint(HttpServletRequest request) {

        String refreshToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER).substring(7);
        String email = jwtTokenProvider.getUserEmail(refreshToken);

        final Integer responses
                = myStoreService.selectPoint(email);

        return ApiResponse.success(responses);

    }

}
