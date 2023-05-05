package com.simpletripbe.moduledomain.batch.mapper;

import com.simpletripbe.moduledomain.batch.dto.AlarmSendDTO;
import com.simpletripbe.moduledomain.batch.entity.Alarm;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = Alarm.class
)
public interface AlarmMapper {

    @Mappings({
            @Mapping(source = "message", target = "message", ignore = true),
    })
    Alarm toEntity(AlarmSendDTO alarmSendDTO);

}
