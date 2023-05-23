package com.simpletripbe.moduledomain.myalarm.repository;

import com.simpletripbe.moduledomain.myalarm.dto.AlarmInfoDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierSelectDTO;

import java.util.List;

public interface MyAlarmRepositoryCustom {

    List<AlarmInfoDTO> findAllByEmail(String email);

}
