package com.simpletripbe.moduledomain.login.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simpletripbe.moduledomain.login.dto.SocialOAuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final GoogleOauth googleOauth;

    /**
     * 소셜 로그인 정보를 받아오는 메서드
     */
    public SocialOAuthDTO socialLogin(String accessToken) throws JsonProcessingException {

        ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(accessToken);
        SocialOAuthDTO result = googleOauth.getUserInfo(userInfoResponse);
        return result;
    }
}