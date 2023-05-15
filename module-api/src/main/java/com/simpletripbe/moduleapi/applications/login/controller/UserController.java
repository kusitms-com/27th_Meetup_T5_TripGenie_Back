package com.simpletripbe.moduleapi.applications.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simpletripbe.moduledomain.login.dto.SignUpReq;
import com.simpletripbe.moduleapi.applications.login.jwt.JwtFilter;
import com.simpletripbe.moduleapi.applications.login.service.UserService;
import com.simpletripbe.modulecommon.common.annotation.AuthUser;
import com.simpletripbe.modulecommon.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    /**
     * 로그인 컨트롤러
     */
    @GetMapping("/oauth/{socialLoginType}")
    public CommonResponse signIn(HttpServletRequest request) throws JsonProcessingException {

        String idToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER).substring(7);

        return userService.signIn(idToken);
    }

    /**
     * 회원가입 컨트롤러
     */
    @PostMapping("/signUp")
    public CommonResponse signUp(@RequestBody SignUpReq signUpReq) {

        return userService.signUp(signUpReq);
    }

    /**
     * 액세스 토큰 재발급 컨트롤러
     */
    @GetMapping("/reissue")
    public CommonResponse reissue(HttpServletRequest request) {

        String refreshToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER).substring(7);

        return userService.reissue(refreshToken);
    }

    /**
     * 로그아웃 컨트롤러
     */
    @GetMapping("/logout")
    public CommonResponse logout(@AuthUser String email) {

        return userService.logout(email);
    }
}