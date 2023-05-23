package com.simpletripbe.moduleapi.applications.mycarrier.service;

import com.simpletripbe.moduledomain.mycarrier.api.MainStorageService;
import com.simpletripbe.moduledomain.mycarrier.dto.StorageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final MainStorageService mainStorageService;

    @Transactional
    public List<StorageDTO> selectAll(String email) {
        return mainStorageService.selectAll(email);
    }

}
