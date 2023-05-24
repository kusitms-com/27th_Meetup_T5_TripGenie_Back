package com.simpletripbe.moduledomain.login.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.moduledomain.login.dto.SocialOAuthDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOauth {

    @Value("${app.google.api.url}")
    private String GOOGLE_API_URL;
    private final ObjectMapper objectMapper;
    /**
     * 구글 리소스 서버에 ID토큰 전달하여 결과 받아오는 메서드
     */
    public ResponseEntity<String> requestUserInfo(String idToken) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        String targetUrl = UriComponentsBuilder.fromHttpUrl(GOOGLE_API_URL).queryParam("id_token", idToken).build().toUriString();

        try {
            ResponseEntity<String> response = restTemplate.exchange(targetUrl, HttpMethod.GET, request, String.class);
            return response;
        } catch (HttpClientErrorException e) {
            throw new CustomException(CommonCode.INVALID_GOOGLE_ID_TOKEN);
        }
    }

    /**
     * 받아온 JSON 데이터를 역직렬화 시키는 메서드
     */
    public SocialOAuthDTO getUserInfo(ResponseEntity<String> userInfoResponse) throws JsonProcessingException {

        SocialOAuthDTO socialOAuthDTO = objectMapper.readValue(userInfoResponse.getBody(), SocialOAuthDTO.class);

        return socialOAuthDTO;
    }
}