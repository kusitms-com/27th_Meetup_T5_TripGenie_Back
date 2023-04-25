package com.simpletripbe.moduledomain.mainpage.api;

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
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.zip.DataFormatException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainPageLogicService {

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

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        final HttpEntity<PermissionRequest> httpEntity
                = new HttpEntity<>(permissionRequest, headers);

        final ResponseEntity<PermissionResponse> responseEntity;

        try {
            responseEntity = restTemplate.exchange(
                    "https://apis.data.go.kr/1262000/EntranceVisaService2/getEntranceVisaList2",
                    HttpMethod.GET,
                    httpEntity,
                    PermissionResponse.class
            );
        } catch (RestClientException e) {
            // request timeout 등 요청 만들다가 에러
            // read timeout 요청 보냈는데 답을 못받아서 에러
            // 응답 받았으나 status code 가 성공이 아님
            log.error("Failed while sending request to Toast SMS Api. toastSmsRequest: {}", permissionRequest, e);
            throw new DataFormatException();
        }

        // 응답 잘 받았고, 내용이 성공
        // 응답 잘 받았고, 내용이 실패
        final PermissionResponse permissionResponse = responseEntity.getBody();
        if (permissionResponse == null) {
            log.error(
                    "Failed to send SMS. permissionRequest: " + permissionRequest + ", permissionResponse: " + permissionResponse);
        }
        return permissionResponse;

    }

}
