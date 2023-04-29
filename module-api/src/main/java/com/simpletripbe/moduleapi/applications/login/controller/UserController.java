package com.simpletripbe.moduleapi.applications.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simpletripbe.moduleapi.applications.login.dto.TokenDTO;
import com.simpletripbe.moduleapi.applications.login.dto.GetSocialOAuthRes;
import com.simpletripbe.moduleapi.applications.login.dto.SignUpReq;
import com.simpletripbe.moduleapi.applications.login.service.OauthService;
import com.simpletripbe.moduleapi.applications.login.service.UserService;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.modulecommon.common.response.CommonResponse;
import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final OauthService oauthService;
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/oauth/{socialLoginType}")
    public CommonResponse signIn(@RequestBody TokenDTO accessTokenReq) throws JsonProcessingException {

        GetSocialOAuthRes res = oauthService.socialLogin(accessTokenReq.getAccessToken());

        Optional<User> userOptional = userRepository.findByEmail(res.getEmail());

        if (userOptional.isPresent()) {
            String accessToken = userService.signIn(userOptional.get());
            return new CommonResponse(CommonCode.OAUTH_CHECK_SUCCESS, Map.of("accessToken", accessToken));
        } else {
            return new CommonResponse(CommonCode.NOT_EXIST_ID, Map.of("userInfo", userOptional.get().getEmail()));
        }
    }

    @PostMapping("/signUp")
    public CommonResponse signUp(@RequestBody SignUpReq signUpReq) {

        User savedUser = userService.signUp(signUpReq);

        if (savedUser != null) {
            return new CommonResponse(CommonCode.SUCCESS);
        } else {
            return new CommonResponse(CommonCode.FAIL);
        }
    }
}
