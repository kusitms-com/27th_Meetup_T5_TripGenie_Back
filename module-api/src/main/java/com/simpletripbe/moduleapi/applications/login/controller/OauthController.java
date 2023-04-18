package com.simpletripbe.moduleapi.applications.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simpletripbe.moduleapi.applications.login.dto.GetSocialOAuthRes;
import com.simpletripbe.moduleapi.applications.login.service.OauthService;
import com.simpletripbe.moduleapi.applications.login.service.UserService;
import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.modulecommon.common.response.CommonResponse;
import com.simpletripbe.moduledomain.login.dto.LoginDTO;
import com.simpletripbe.moduledomain.login.dto.UserDTO;
import com.simpletripbe.moduledomain.login.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/oauth")
public class OauthController {

    private final OauthService oauthService;
    private final UserService userService;
    private final String DEFAULT_PICTURE_URL = "https://toppng.com//public/uploads/preview/user-account-management-logo-user-icon-11562867145a56rus2zwu.png";

    /**
     * google social login api
     * @param socialLoginType
     * @throws IOException
     */
    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(@PathVariable(name = "socialLoginType") String socialLoginType) throws IOException {
        oauthService.request(socialLoginType);
    }

    @GetMapping(value = "/{socialLoginType}/callback")
    public CommonResponse callback(
            @PathVariable(name = "socialLoginType") String socialLoginType,
            @RequestParam(name = "code") String code) throws JsonProcessingException {

        GetSocialOAuthRes res = oauthService.oauthLogin(socialLoginType, code);

        UserDTO user = userService.findUserByEmail(res.getEmail());

        if (user == null) {
            return new CommonResponse(CommonCode.OAUTH_CHECK_SUCCESS, Map.of("userInfo", res));
        } else {
            return new CommonResponse(CommonCode.USER_ALREADY_EXIST, Map.of("userInfo", new GetSocialOAuthRes(user)));
        }

    }

    /**
     * login api
     * @param requestBody
     * @return
     */
    @PostMapping("/signIn")
    public CommonResponse signIn(@RequestBody LoginDTO requestBody) {
        UserDTO user = userService.checkExistUser(requestBody.getEmail(), requestBody.getPassword());

        HashMap<String, String> attribute = new HashMap<>();
        attribute.put("id", user.getId().toString());
        attribute.put("nickname", user.getNickname());
        attribute.put("email", user.getEmail());
        return new CommonResponse(CommonCode.SUCCESS, attribute);
    }

    /**
     * 회원가입 api
     * @param userDetailDto
     * @return
     */
    @PostMapping("/signUp")
    public CommonResponse signUp(@RequestBody UserDetailDTO userDetailDto) {

        if (userService.findAllUserByEmail(userDetailDto.getEmail()).size() > 0)
            throw new CustomException(CommonCode.USER_ALREADY_EXIST);
        if (userService.findAllUserByNickname(userDetailDto.getNickname()).size() > 0)
            throw new CustomException(CommonCode.NICKNAME_ALREADY_EXIST);
        if (userDetailDto.getPictureUrl() == null) {
            userDetailDto.setPictureUrl(DEFAULT_PICTURE_URL);
        }

        try {
            UserDTO savedUser = userService.saveUser(userDetailDto);

            if (savedUser != null) return new CommonResponse(CommonCode.SUCCESS);
            return new CommonResponse(CommonCode.FAIL);
        } catch (Exception e) {
            throw new CustomException(CommonCode.FAIL);
        }
    }

}
