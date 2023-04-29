package com.simpletripbe.moduleapi.applications.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpletripbe.moduleapi.applications.login.dto.GoogleUser;
import com.simpletripbe.moduleapi.applications.login.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOauth {

    @Value("${app.google.userinfo.url}")
    private String GOOGLE_SNS_USERINFO_URL;

    private final ObjectMapper objectMapper;

    public ResponseEntity<String> requestUserInfo(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();

        //header에 accessToken을 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        //HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_SNS_USERINFO_URL, HttpMethod.GET, request, String.class);

        return response;
    }

    public GoogleUser getUserInfo(ResponseEntity<String> userInfoResponse) throws JsonProcessingException {

        GoogleUser googleUser = objectMapper.readValue(userInfoResponse.getBody(), GoogleUser.class);
        return googleUser;
    }
}