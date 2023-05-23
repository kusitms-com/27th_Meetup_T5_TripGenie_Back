package com.simpletripbe.moduleapi.applications.myalarm.service;

import com.simpletripbe.moduledomain.myalarm.api.MainAlarmService;
import com.simpletripbe.moduledomain.myalarm.dto.AlarmInfoDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierSelectDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyAlarmService {

    private final MainAlarmService mainAlarmService;

    @Transactional(readOnly = true)
    public List<AlarmInfoDTO> selectAllAlarms(String email) {
        return mainAlarmService
                .selectAllAlarms(email);
    }

}
