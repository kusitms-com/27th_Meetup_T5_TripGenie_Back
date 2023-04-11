package com.simpletripbe.moduleapi.applications.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simpletripbe.moduleapi.applications.login.dto.GetSocialOAuthRes;
import com.simpletripbe.moduleapi.applications.login.service.OauthService;
import com.simpletripbe.moduleapi.applications.login.service.UserService;
import com.simpletripbe.modulecommon.common.annotation.Valid;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.modulecommon.common.response.CommonResponse;
import com.simpletripbe.moduledomain.community.dto.LoginDTO;
import com.simpletripbe.moduledomain.community.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/oauth")
public class OauthController {

    private final OauthService oauthService;
    private final UserService userService;

    @GetMapping(value="/{socialLoginType}")
    public void socialLoginType(@PathVariable(name="socialLoginType") String socialLoginType) throws IOException {
        oauthService.request(socialLoginType);
    }

    @GetMapping(value="/{socialLoginType}/callback")
    public CommonResponse callback(
            @PathVariable(name="socialLoginType") String socialLoginType,
            @RequestParam(name="code") String code) throws JsonProcessingException {

        GetSocialOAuthRes res = oauthService.oauthLogin(socialLoginType, code);

        UserDTO user = userService.findUserByEmail(res.getEmail());

        if (user == null) {
            return new CommonResponse(CommonCode.OAUTH_CHECK_SUCCESS, Map.of("userInfo", res));
        } else {
            // 유저가 이미 존재하는 경우 어떻게 Gateway에 데이터를 넘겨줄지에 따라 attribute 객체가 수정될 수 있음
            return new CommonResponse(CommonCode.USER_ALREADY_EXIST, Map.of("userInfo", new GetSocialOAuthRes(user)));
        }

    }

    @PostMapping("/signIn")
    public CommonResponse signIn(@RequestBody @Valid LoginDTO requestBody) {
        UserDTO user = userService.checkExistUser(requestBody.getEmail(), requestBody.getPassword());
        //pasing 하기 쉽게 HashMap 사용(depth 최대한 얕게)
        HashMap<String, String> attribute = new HashMap<>();
        attribute.put("id", user.getId().toString());
        attribute.put("nickname", user.getNickname().toString());
        attribute.put("email", user.getEmail());
        return new CommonResponse(CommonCode.SUCCESS, attribute);
    }

}
