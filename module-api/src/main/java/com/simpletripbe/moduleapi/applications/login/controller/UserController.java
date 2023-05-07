package com.simpletripbe.moduleapi.applications.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simpletripbe.moduleapi.applications.login.dto.SocialOAuthDTO;
import com.simpletripbe.moduleapi.applications.login.dto.SignUpReq;
import com.simpletripbe.moduleapi.applications.login.jwt.JwtFilter;
import com.simpletripbe.moduleapi.applications.login.service.OauthService;
import com.simpletripbe.moduleapi.applications.login.service.UserService;
import com.simpletripbe.modulecommon.common.response.CommonResponse;
import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final OauthService oauthService;
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/oauth/{socialLoginType}")
    public CommonResponse signIn(HttpServletRequest request) throws JsonProcessingException {

        String accessToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER).substring(7);

        SocialOAuthDTO socialOAuthDTO = oauthService.socialLogin(accessToken);
        Optional<User> userOptional = userRepository.findByEmail(socialOAuthDTO.getEmail());

        CommonResponse commonResponse = userService.signIn(userOptional, socialOAuthDTO);

        return commonResponse;
    }

    @PostMapping("/signUp")
    public CommonResponse signUp(@RequestBody SignUpReq signUpReq, HttpServletRequest request) {

        CommonResponse commonResponse = userService.signUp(signUpReq);

        return commonResponse;
    }

    @GetMapping("/reissue")
    public CommonResponse reissue(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER).substring(7);

        CommonResponse commonResponse = userService.reissue(refreshToken);

        return commonResponse;
    }

    @GetMapping("/logout")
    public CommonResponse logout(HttpServletRequest request) {

        // SecurityContextHolder에 저장된 Authentication 객체를 통해 email 가져옴
        UserDetails authentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CommonResponse commonResponse = userService.logout(authentication.getUsername());

        return commonResponse;
    }
}
