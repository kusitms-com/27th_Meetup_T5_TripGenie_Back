package com.simpletripbe.moduledomain.mainpage.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpletripbe.moduledomain.mainpage.dto.MainPageListDTO;
import com.simpletripbe.moduledomain.mainpage.dto.OrderType;
import com.simpletripbe.moduledomain.mainpage.dto.dataApi.PermissionRequest;
import com.simpletripbe.moduledomain.mainpage.dto.dataApi.PermissionResponse;
import com.simpletripbe.moduledomain.mainpage.entity.MainPage;
import com.simpletripbe.moduledomain.mainpage.mapper.MainPageMapper;
import com.simpletripbe.moduledomain.mainpage.repository.MainPageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainPageLogicService {

    private final ObjectMapper objectMapper;
    private final MainPageRepository mainPageRepository;
    private final MainPageMapper mainPageMapper;

    public List<MainPageListDTO> selectAll(OrderType orderType) {

        List<MainPage> entityList = null;

        if (orderType.equals(OrderType.ALL)) {
            entityList = mainPageRepository.findAll();
        } else if (orderType.equals(OrderType.CONTINENT)) {
            entityList = mainPageRepository.findBySort(orderType);
        } else if (orderType.equals(OrderType.SPELL)) {
            //TODO -- 가나다 순 정렬 어떻게 할지 고민
            entityList = mainPageRepository.findBySort(orderType);
        }

        List<MainPageListDTO> resultList = mainPageMapper.toDTO(entityList);

        return resultList;
    }

    public List<MainPageListDTO> selectBySearchWord(String searchWord) {

        List<MainPage> entityList = mainPageRepository.findBySearchWord(searchWord);

        List<MainPageListDTO> resultList = mainPageMapper.toDTO(entityList);

        return resultList;
    }

    public PermissionResponse selectEntryPermitRequirements(PermissionRequest permissionRequest) throws DataFormatException {

        RestTemplate restTemplate = new RestTemplate();

        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("serviceKey", permissionRequest.getServiceKey());
        params.add("returnType", permissionRequest.getReturnType());
        params.add("numOfRows", String.valueOf(permissionRequest.getNumOfRows()));
        params.add("pageNo", String.valueOf(permissionRequest.getPageNo()));
        params.add("country_nm", permissionRequest.getCountry_nm());
        params.add("country_iso_alp2", permissionRequest.getCountry_iso_alp2());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<PermissionRequest> httpEntity
                = new HttpEntity<>(permissionRequest, headers);

        final ResponseEntity<PermissionResponse> responseEntity;

        String url = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("apis.data.go.kr")
                .pathSegment("1262000/EntranceVisaService2/getEntranceVisaList2")
                .queryParams(params)
                .build().toUriString();

        responseEntity = restTemplate.exchange(
                URI.create(url),
                HttpMethod.GET,
                httpEntity,
                PermissionResponse.class
        );
        log.info("responseEntity::" + responseEntity);

        final PermissionResponse permissionResponse = responseEntity.getBody();
        if (permissionResponse == null) {
            log.error(
                    "Failed to send permissionRequest: " + permissionRequest + ", permissionResponse: " + permissionResponse);
        }
        return permissionResponse;

    }

}
