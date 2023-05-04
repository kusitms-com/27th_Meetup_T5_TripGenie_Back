package com.simpletripbe.moduleapi.applications.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        ObjectMapper objectMapper = new ObjectMapper();
        Map userInfo = objectMapper.convertValue(res, Map.class);
        Optional<User> userOptional = userRepository.findByEmail(res.getEmail());

        if (userOptional.isPresent()) {
            // 필수 정보가 입력되지 않은 경우 -> 즉, 회원가입이 제대로 처리되지 않은 경우
            if (userOptional.get().getNickname() == null) {
                String accessToken = jwtTokenProvider.generateAccessToken(userOptional.get());
                response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
                return new CommonResponse(CommonCode.SIGNUP_REQUIRED, userInfo);
            } else { // 회원인 경우
                Map<String, String> token = userService.signIn(userOptional.get());
                response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token.get("accessToken") + ", Bearer " + token.get("refreshToken"));
                return new CommonResponse(CommonCode.OAUTH_CHECK_SUCCESS, userInfo);
            }
        } else {
            // 최초 로그인인 경우 -> 추가 정보를 입력받기 위해 jwt access token 생성을 위한 임시 회원가입
            User user = new User(res.getEmail());
            userService.tmpSignUp(user);

            String accessToken = jwtTokenProvider.generateAccessToken(user);
            response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
            return new CommonResponse(CommonCode.FIRST_TIME_LOGIN, userInfo);
        }
    }

    @PostMapping("/signUp")
    public CommonResponse signUp(@RequestBody SignUpReq signUpReq, HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = userService.signUp(signUpReq);

        String accessToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER);
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, accessToken + ", Bearer " + refreshToken);

        return new CommonResponse(CommonCode.SUCCESS);
    }

    @GetMapping("/reissue")
    public CommonResponse reissue(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER).substring(7);
        String accessToken = jwtTokenProvider.reissueAtk(refreshToken);

        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return new CommonResponse(CommonCode.SUCCESS);
    }

    @GetMapping("/logout")
    public CommonResponse logout(HttpServletRequest request) {

        // SecurityContextHolder에 저장된 Authentication 객체를 통해 email 가져옴
        UserDetails authentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = authentication.getUsername();

        userService.logout(email);

        return new CommonResponse(CommonCode.SUCCESS);
    }
}
