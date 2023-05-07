package com.simpletripbe.moduleapi.applications.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simpletripbe.moduleapi.applications.login.dto.SocialOAuthDTO;
import com.simpletripbe.moduleapi.applications.login.dto.GoogleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final GoogleOauth googleOauth;

    public SocialOAuthDTO socialLogin(String accessToken) throws JsonProcessingException {

        SocialOAuthDTO result;
        ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(accessToken);
        GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);
        result = new SocialOAuthDTO(googleUser.getEmail(), googleUser.getName(), googleUser.getPicture());
        return result;
    }
}
