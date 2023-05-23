package com.simpletripbe.moduledomain.myalarm.api;

import com.simpletripbe.moduledomain.myalarm.dto.AlarmInfoDTO;
import com.simpletripbe.moduledomain.myalarm.repository.MyAlarmRepository;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierSelectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainAlarmService {

    private final MyAlarmRepository myAlarmRepository;

    public List<AlarmInfoDTO> selectAllAlarms(String email) {

        List<AlarmInfoDTO> entityResult = myAlarmRepository.findAllByEmail(email);

        return entityResult;
    }

}
