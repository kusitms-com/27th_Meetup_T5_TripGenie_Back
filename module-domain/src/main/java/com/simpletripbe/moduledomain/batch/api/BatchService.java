package com.simpletripbe.moduledomain.batch.api;

import com.simpletripbe.moduledomain.batch.dto.AlarmSendDTO;
import com.simpletripbe.moduledomain.batch.dto.MyBagSaveDTO;
import com.simpletripbe.moduledomain.batch.entity.Alarm;
import com.simpletripbe.moduledomain.batch.mapper.AlarmMapper;
import com.simpletripbe.moduledomain.batch.repository.AlarmRepository;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.mapper.MyCarrierMapper;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final MyCarrierRepository myCarrierRepository;
    private final MyCarrierMapper myCarrierMapper;
    private final AlarmRepository alarmRepository;
    private final AlarmMapper alarmMapper;

    public void saveStartAlarm(AlarmSendDTO alarmSendDTO) {

        Alarm alarm = alarmMapper.toEntity(alarmSendDTO);
        alarmRepository.save(alarm);

    }

    public void saveMyBag(MyBagSaveDTO dto) {

        MyCarrier myCarrier = myCarrierMapper.toMyBagEntity(dto);
        myCarrierRepository.save(myCarrier);

    }

    public void saveEndAlarm(AlarmSendDTO alarmSendDTO) {

        Alarm alarm = alarmMapper.toEntity(alarmSendDTO);
        alarmRepository.save(alarm);

    }

}
