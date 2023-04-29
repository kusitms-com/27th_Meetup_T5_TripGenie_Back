package com.simpletripbe.moduleapi.applications.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simpletripbe.moduleapi.applications.login.dto.TokenDTO;
import com.simpletripbe.moduleapi.applications.login.dto.GetSocialOAuthRes;
import com.simpletripbe.moduleapi.applications.login.dto.SignUpReq;
import com.simpletripbe.moduleapi.applications.login.jwt.JwtFilter;
import com.simpletripbe.moduleapi.applications.login.jwt.JwtTokenProvider;
import com.simpletripbe.moduleapi.applications.login.service.OauthService;
import com.simpletripbe.moduleapi.applications.login.service.UserService;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.modulecommon.common.response.CommonResponse;
import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final OauthService oauthService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/oauth/{socialLoginType}")
    public CommonResponse signIn(@RequestBody TokenDTO accessTokenReq, HttpServletResponse response) throws JsonProcessingException {

        GetSocialOAuthRes res = oauthService.socialLogin(accessTokenReq.getAccessToken());

        Optional<User> userOptional = userRepository.findByEmail(res.getEmail());

        if (userOptional.isPresent()) {
            // 필수 정보가 입력되지 않은 경우 -> 즉, 회원가입이 제대로 처리되지 않은 경우
            if (userOptional.get().getNickname() == null) {
                String accessToken = jwtTokenProvider.generateAccessToken(userOptional.get());
                response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
                return new CommonResponse(CommonCode.SIGNUP_REQUIRED, Map.of("accessToken", accessToken));
            } else { // 회원인 경우
                String accessToken = userService.signIn(userOptional.get());
                response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
                return new CommonResponse(CommonCode.OAUTH_CHECK_SUCCESS, Map.of("accessToken", accessToken));
            }
        } else {
            // 추가 정보를 입력받기 위해 jwt access token 생성을 위한 임시 회원가입
            User user = new User(res.getEmail());
            userService.tmpSignUp(user);

            String accessToken = jwtTokenProvider.generateAccessToken(user);
            response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
            return new CommonResponse(CommonCode.SIGNUP_REQUIRED, Map.of("accessToken", accessToken));
        }
    }

    @PostMapping("/signUp")
    public CommonResponse signUp(@RequestBody SignUpReq signUpReq, HttpServletRequest request, HttpServletResponse response) {

        User savedUser = userService.signUp(signUpReq);
        String accessToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER);
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, accessToken);

        if (savedUser != null) {
            return new CommonResponse(CommonCode.SUCCESS);
        } else {
            return new CommonResponse(CommonCode.FAIL);
        }
    }
}
